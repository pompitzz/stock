package me.sun.apiserver.application.stock

import me.sun.apiserver.StockException
import me.sun.apiserver.api.MyPage
import me.sun.apiserver.application.user.UserInterestStockService
import me.sun.apiserver.common.USER_ID_HOLDER
import me.sun.apiserver.domain.entity.historicalstockprice.repo.HistoricalStockPriceRepository
import me.sun.apiserver.domain.entity.stock.Stock
import me.sun.apiserver.domain.entity.stock.repo.StockRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StockService(
    private val stockRepository: StockRepository,
    private val historicalRepository: HistoricalStockPriceRepository,
    private val userInterestStockService: UserInterestStockService
) {
    fun search(query: String, pageable: Pageable): MyPage<StockSummary> {
        val stockPage = stockRepository.search(query, pageable)
        return MyPage.from(stockPage) { toStockSummaries(it) }
    }

    fun findStockSummaries(stockId: List<Long>): List<StockSummary> {
        if (stockId.isEmpty()) return emptyList()
        val stocks = stockRepository.findAllById(stockId)
        return toStockSummaries(stocks)
    }

    fun findStockContext(symbol: String, period: Long): StockContext {
        val stock = stockRepository.findBySymbol(symbol) ?: throw StockException("cannot find stock. symbol: $symbol")
        val targetDate = LocalDate.now().minusDays(period)
        val historicalStockPrices = historicalRepository.findAllAfterOrEqualToTargetDate(stock.id, targetDate)
        return StockContext(
            stockSummary = StockSummary.from(stock),
            historicalPrices = historicalStockPrices.map { SimpleHistoricalPrice(it.close, it.date) }
        )
    }

    private fun toStockSummaries(stocks: List<Stock>): List<StockSummary> {
        if (stocks.isEmpty()) return emptyList()
        val interestStockIds = userInterestStockService.findExistsStockIds(USER_ID_HOLDER.get(), stocks.map { it.id })
        return stocks.map { StockSummary.from(it, interestStockIds.contains(it.id)) }
    }
}
