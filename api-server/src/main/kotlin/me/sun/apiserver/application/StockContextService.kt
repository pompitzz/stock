package me.sun.apiserver.application

import me.sun.apiserver.StockException
import me.sun.apiserver.domain.entity.historicalstockprice.repository.HistoricalStockPriceRepository
import me.sun.apiserver.domain.entity.stock.repository.StockRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StockContextService(
    private val stockRepository: StockRepository,
    private val historicalStockPriceRepository: HistoricalStockPriceRepository
) {
    fun getStockContext(symbol: String, period: Long): StockContext {
        val stock = stockRepository.findBySymbol(symbol) ?: throw StockException("cannot find stock. symbol: $symbol")
        val historicalStockPrices =
            historicalStockPriceRepository.findAllAfterOrEqualToTargetDate(stock.id, LocalDate.now().minusDays(period))
        return StockContext(
            stockSummary = StockSummary.fromEntity(stock),
            historicalPrices = historicalStockPrices.map { SimpleHistoricalPrice(it.close, it.date) }
        )
    }
}
