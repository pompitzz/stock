package me.sun.apiserver.domain.entity.historicalstockprice.repo

import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import java.time.LocalDate

interface HistoricalStockPriceRepositoryCustom {
    fun findAllAfterOrEqualToTargetDate(stockId: Long, targetDate: LocalDate): List<HistoricalStockPrice>
}
