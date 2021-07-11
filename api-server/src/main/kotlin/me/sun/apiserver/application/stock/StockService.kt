package me.sun.apiserver.application.stock

import me.sun.apiserver.StockException
import me.sun.apiserver.api.MyPage
import me.sun.apiserver.application.user.UserInterestStockService
import me.sun.apiserver.common.USER_ID_HOLDER
import me.sun.apiserver.domain.entity.stock.Stock
import me.sun.apiserver.domain.entity.stock.repo.StockRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository,
    private val historicalStockPriceService: HistoricalStockPriceService,
    private val userInterestStockService: UserInterestStockService,
) {
    fun search(query: String, pageable: Pageable): MyPage<StockContext> {
        val stockPage = stockRepository.search(query, pageable)
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), stockPage.content.map { it.id })
        return MyPage.from(stockPage) { content ->
            content.map { doFindStockContext(it, StockSearchPeriodType.M_1, interestStockIds) }
        }
    }

    fun findStockContexts(stockId: List<Long>): List<StockContext> {
        if (stockId.isEmpty()) return emptyList()
        val stocks = stockRepository.findAllById(stockId)
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), stocks.map { it.id })
        return stocks.map { doFindStockContext(it, StockSearchPeriodType.M_1, interestStockIds) }
    }

    fun findStockContext(symbol: String, periodType: StockSearchPeriodType): StockContext {
        val stock = stockRepository.findBySymbol(symbol) ?: throw StockException("cannot find stock. symbol: $symbol")
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
            .map { SimpleHistoricalPrice(it.close, it.date) }
        return StockContext(
            stockDetail = StockDetail.from(stock, historicalStockPrices),
            interest = interestStockIds.contains(stock.id)
        )
    }
}
