package me.sun.apiserver.application

import me.sun.apiserver.application.stock.HistoricalStockPriceDto
import me.sun.apiserver.application.stock.StockContext
import me.sun.apiserver.application.stock.StockDetail
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.entity.stock.Stock

object StockContextBuilder {
    fun buildStockContexts(
        stocks: List<Stock>,
        historicalStockPriceProvider: (stock: Stock) -> List<HistoricalStockPrice> = { emptyList() },
    ): List<StockContext> {
        return stocks.map { buildStockContext(it, historicalStockPriceProvider(it)) }
    }

    fun buildStockContext(
        stock: Stock,
        historicalStockPrices: List<HistoricalStockPrice> = emptyList(),
        interestStockIds: Set<Long> = emptySet(),
    ) = StockContext(
        stockDetail = StockDetail.from(stock),
        interest = interestStockIds.contains(stock.id),
        historicalPrices = historicalStockPrices.map { HistoricalStockPriceDto.from(it) },
    )
}
