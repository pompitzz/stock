package me.sun.apiserver.application.stock

import me.sun.apiserver.common.logger
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.entity.historicalstockprice.repo.HistoricalStockPriceRepository
import me.sun.apiserver.domain.entity.stock.Stock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class StockSynchronizer(
    private val stockSyncContextProvider: StockSyncContextProvider,
    private val historicalStockPriceRepository: HistoricalStockPriceRepository,
    private val stockUpdater: StockUpdater,
) {

    private val log = logger<StockSynchronizer>()

    @Transactional
    fun sync(stock: Stock) {
        if (isSyncPassTarget(stock)) {
            log.info("sync pass. symbol: {}, priceDate: {}", stock.symbol, stock.stockPrice.date)
            return
        }
        log.info("start stock sync. symbol: {}", stock.symbol)
        val context = stockSyncContextProvider.getContext(stock.symbol, stock.id)
        if (context == null) {
            log.info("sync pass because context is null.")
            return
        }
        val historicalStockPrices = extractHistoricalStockPrices(context.historicalStockPrices, stock.id)
        stockUpdater.updateStockPriceAndHistoricalPrices(stock, context.stockPrice, historicalStockPrices)
        log.info("end stock sync. symbol: {}", stock.symbol)
    }

    // TODO: improve algorithm
    private fun isSyncPassTarget(stock: Stock): Boolean {
        val now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of(stock.timeZone))
        if (stock.isMargetOpening(now)) {
            return true
        }

        if (now.toLocalDate().isEqual(stock.stockPrice.date)) {
            return true
        }

        // TODO: add flag to sync only target stock
        return false
    }

    private fun extractHistoricalStockPrices(historicalStockPrices: List<HistoricalStockPrice>, stockId: Long): List<HistoricalStockPrice> {
        val latestStockPriceDate = historicalStockPriceRepository.findLatestPrice(stockId).date
        return historicalStockPrices
            .filter { it.date.isAfter(latestStockPriceDate) }
            .toList()
    }
}
