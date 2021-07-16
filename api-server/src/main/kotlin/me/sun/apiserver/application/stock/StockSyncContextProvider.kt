package me.sun.apiserver.application.stock

import me.sun.apiserver.common.logger
import me.sun.apiserver.domain.entity.StockPrice
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.service.provider.HistoricalStockPricesProvider
import me.sun.apiserver.domain.service.provider.StockPriceProvider
import org.springframework.stereotype.Component

@Component
class StockSyncContextProvider(
    private val stockPriceProvider: StockPriceProvider,
    private val historicalStockPricesProvider: HistoricalStockPricesProvider,
) {

    private val log = logger<StockSyncContextProvider>()

    fun getContext(symbol: String, stockId: Long): StockSyncContext? {
        return try {
            val stockPrice = stockPriceProvider.getStockPrice(symbol)
            val historicalStockPrices = historicalStockPricesProvider.getHistoricalStockPrices(symbol, stockId)
            StockSyncContext(
                stockPrice = stockPrice,
                historicalStockPrices = historicalStockPrices
            )
        } catch (e: Exception) {
            log.error("fail getting sync data.", e)
            null
        }
    }
}

data class StockSyncContext(
    val stockPrice: StockPrice,
    val historicalStockPrices: List<HistoricalStockPrice>,
)
