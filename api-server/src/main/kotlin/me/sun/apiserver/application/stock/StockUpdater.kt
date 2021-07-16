package me.sun.apiserver.application.stock

import me.sun.apiserver.domain.entity.StockPrice
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.entity.historicalstockprice.repo.HistoricalStockPriceRepository
import me.sun.apiserver.domain.entity.stock.Stock
import me.sun.apiserver.domain.entity.stock.repo.StockRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StockUpdater(
    private val stockRepository: StockRepository,
    private val historicalStockPriceRepository: HistoricalStockPriceRepository,
) {
    @Transactional
    fun updateStockPriceAndHistoricalPrices(
        stock: Stock,
        stockPrice: StockPrice,
        historicalStockPrices: List<HistoricalStockPrice>,
    ) {
        stock.updateStockPrice(stockPrice)
        stockRepository.save(stock)
        historicalStockPriceRepository.saveAll(historicalStockPrices)
    }
}
