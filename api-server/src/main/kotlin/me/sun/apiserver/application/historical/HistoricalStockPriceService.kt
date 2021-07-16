package me.sun.apiserver.application.historical

import me.sun.apiserver.application.stock.StockSearchPeriodType
import me.sun.apiserver.common.logger
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.entity.historicalstockprice.repo.HistoricalStockPriceRepository
import me.sun.apiserver.domain.entity.stock.Stock
import me.sun.apiserver.infrastructure.jpa.OrderSpecifierResolver
import org.springframework.stereotype.Service

@Service
class HistoricalStockPriceService(
    private val historicalRepository: HistoricalStockPriceRepository,
    private val historicalStockPriceSelector: HistoricalStockPriceSelector
) {

    private val log = logger<OrderSpecifierResolver>()

    fun findHistoricalStockPriceByPeriodType(stock: Stock, periodType: StockSearchPeriodType): List<HistoricalStockPrice> {
        return try {
            val historicalStockPrices = historicalRepository.findAllAfterOrEqualToTargetDate(stock.id, periodType.getStartDate())
            historicalStockPriceSelector.selectBySelectionTimeUnit(historicalStockPrices, periodType.selectionTimeUnit)
        } catch (e: Exception) {
            log.error("fail find historical stock prices. symbol: {}", stock.symbol, e)
            emptyList()
        }
    }
}
