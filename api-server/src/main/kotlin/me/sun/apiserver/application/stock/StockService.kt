package me.sun.apiserver.application.stock

import me.sun.apiserver.api.MyPage
import me.sun.apiserver.application.historical.HistoricalStockPriceService
import me.sun.apiserver.application.user.UserInterestStockService
import me.sun.apiserver.common.USER_ID_HOLDER
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.entity.stock.Stock
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockQueryService: StockQueryService,
    private val historicalStockPriceService: HistoricalStockPriceService,
    private val userInterestStockService: UserInterestStockService,
) {
    fun search(searchCondition: StockSearchCondition, pageable: Pageable): MyPage<StockContext> {
        val stockPage = stockQueryService.search(searchCondition.query, pageable)
        val historicalStockPriceProvider: (stock: Stock) -> List<HistoricalStockPrice> =
            if (searchCondition.withDetails) ({ findHistoricalStockPrices(it, searchCondition.periodType) })
            else ({ emptyList() })
        return MyPage.from(stockPage) { buildStockContexts(it, historicalStockPriceProvider) }
    }

    fun findStockContexts(stockIds: List<Long>): List<StockContext> {
        if (stockIds.isEmpty()) return emptyList()
        val stocks = stockQueryService.findAllById(stockIds)
        return buildStockContexts(stocks) { findHistoricalStockPrices(it, StockSearchPeriodType.M_1) }
    }

    fun findStockContext(symbol: String, periodType: StockSearchPeriodType): StockContext {
        val stock = stockQueryService.findBySymbol(symbol)
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), listOf(stock.id))
        val historicalStockPrices = findHistoricalStockPrices(stock, periodType)
        return buildStockContext(stock, historicalStockPrices, interestStockIds)
    }

    private fun findHistoricalStockPrices(stock: Stock, periodType: StockSearchPeriodType) = historicalStockPriceService.findHistoricalStockPriceByPeriodType(stock, periodType)

    private fun buildStockContexts(
        stocks: List<Stock>,
        historicalStockPriceProvider: (stock: Stock) -> List<HistoricalStockPrice>,
    ): List<StockContext> {
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), stocks.map { it.id })
        return stocks.map { buildStockContext(it, historicalStockPriceProvider(it), interestStockIds) }
    }

    private fun buildStockContext(
        stock: Stock,
        historicalStockPrices: List<HistoricalStockPrice> = emptyList(),
        interestStockIds: Set<Long> = emptySet(),
    ) = StockContext(
        stockDetail = StockDetail.from(stock),
        interest = interestStockIds.contains(stock.id),
        historicalPrices = historicalStockPrices.map { HistoricalStockPriceDto.from(it) },
    )
}
