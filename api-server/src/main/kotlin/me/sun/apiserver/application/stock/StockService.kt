package me.sun.apiserver.application.stock

import me.sun.apiserver.api.MyPage
import me.sun.apiserver.application.historical.HistoricalStockPriceService
import me.sun.apiserver.application.user.UserInterestStockService
import me.sun.apiserver.common.USER_ID_HOLDER
import me.sun.apiserver.domain.entity.stock.Stock
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockQueryService: StockQueryService,
    private val historicalStockPriceService: HistoricalStockPriceService,
    private val userInterestStockService: UserInterestStockService,
) {
    fun search(query: String, pageable: Pageable): MyPage<StockContext> {
        val stockPage = stockQueryService.search(query, pageable)
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), stockPage.content.map { it.id })
        return MyPage.from(stockPage) { content ->
            content.map { doFindStockContext(it, StockSearchPeriodType.M_1, interestStockIds) }
        }
    }

    fun findStockContexts(stockIds: List<Long>): List<StockContext> {
        if (stockIds.isEmpty()) return emptyList()
        val stocks = stockQueryService.findAllById(stockIds)
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), stocks.map { it.id })
        return stocks.map { doFindStockContext(it, StockSearchPeriodType.M_1, interestStockIds) }
    }

    fun findStockContext(symbol: String, periodType: StockSearchPeriodType): StockContext {
        val stock = stockQueryService.findBySymbol(symbol)
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), listOf(stock.id))
        return doFindStockContext(stock, periodType, interestStockIds)
    }

    private fun doFindStockContext(
        stock: Stock,
        periodType: StockSearchPeriodType,
        interestStockIds: Set<Long>,
    ): StockContext {
        val historicalStockPrices = historicalStockPriceService
            .findHistoricalStockPriceByPeriodType(stock, periodType)
            .map { HistoricalStockPriceDto.from(it) }
        return StockContext(
            stockDetail = StockDetail.from(stock),
            interest = interestStockIds.contains(stock.id),
            historicalPrices = historicalStockPrices,
        )
    }
}
