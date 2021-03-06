package me.sun.apiserver.domain.service.provider

import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice

interface HistoricalStockPricesProvider {
    fun getHistoricalStockPrices(symbol: String, stockId: Long): List<HistoricalStockPrice>
}
