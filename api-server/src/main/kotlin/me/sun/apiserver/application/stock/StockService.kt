package me.sun.apiserver.application.stock

import me.sun.apiserver.StockException
import me.sun.apiserver.api.MyPage
import me.sun.apiserver.domain.entity.historicalstockprice.repository.HistoricalStockPriceRepository
import me.sun.apiserver.domain.entity.stock.repository.StockRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StockService(
    private val stockRepository: StockRepository,
    private val historicalRepository: HistoricalStockPriceRepository
) {
    fun search(query: String, pageable: Pageable): MyPage<StockSummary> {
        val stockPage = stockRepository.search(query, pageable)
        val stockSummaryPage = stockPage.map { StockSummary.fromEntity(it) }
        return MyPage.from(stockSummaryPage)
    }

    fun findStockContext(symbol: String, period: Long): StockContext {
        val stock = stockRepository.findBySymbol(symbol) ?: throw StockException("cannot find stock. symbol: $symbol")
        val targetDate = LocalDate.now().minusDays(period)
        val historicalStockPrices = historicalRepository.findAllAfterOrEqualToTargetDate(stock.id, targetDate)
        return StockContext(
            stockSummary = StockSummary.fromEntity(stock),
            historicalPrices = historicalStockPrices.map { SimpleHistoricalPrice(it.close, it.date) }
        )
    }
}
