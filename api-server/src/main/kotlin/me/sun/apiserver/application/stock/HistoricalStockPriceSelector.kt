package me.sun.apiserver.application.stock

import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.temporal.WeekFields

@Component
class HistoricalStockPriceSelector {
    fun selectBySelectionTimeUnit(
        historicalStockPrices: List<HistoricalStockPrice>,
        selectionTimeUnit: StockSearchSelectionTimeUnit,
    ): List<HistoricalStockPrice> = when (selectionTimeUnit) {
        StockSearchSelectionTimeUnit.DAY -> historicalStockPrices.sortedBy { it.date }
        StockSearchSelectionTimeUnit.WEEK -> historicalStockPrices.selectByWeek().sortedBy { it.date }
        StockSearchSelectionTimeUnit.MONTH -> historicalStockPrices.selectByMonth().sortedBy { it.date }
    }

    private fun List<HistoricalStockPrice>.selectByWeek(): List<HistoricalStockPrice> {
        val latestDate = this.map { it.date }.maxOrNull() ?: throw IllegalArgumentException("historical prices should not be empty")
        val targetDayOfWeek = latestDate.dayOfWeek.value
        val isSame: (date: LocalDate) -> Boolean = { it.dayOfWeek.value == targetDayOfWeek }
        val isBefore: (date: LocalDate) -> Boolean = { it.dayOfWeek.value < targetDayOfWeek }
        return this.groupByYear().values
            .map { pricesWithSameYear -> pricesWithSameYear.groupByWeek().values.map { it.selectOneClosestToTargetDate(isSame, isBefore) } }
            .flatten()
    }

    private fun List<HistoricalStockPrice>.selectByMonth(): List<HistoricalStockPrice> {
        val latestDate = this.map { it.date }.maxOrNull() ?: throw IllegalArgumentException("historical prices should not be empty")
        val targetDayOfMonth = latestDate.dayOfMonth
        val isSame: (date: LocalDate) -> Boolean = { it.dayOfMonth == targetDayOfMonth }
        val isBefore: (date: LocalDate) -> Boolean = { it.dayOfMonth < targetDayOfMonth }
        return this.groupByYear().values
            .map { pricesWithSameYear -> pricesWithSameYear.groupByMonth().values.map { it.selectOneClosestToTargetDate(isSame, isBefore) } }
            .flatten()
    }

    private fun List<HistoricalStockPrice>.selectOneClosestToTargetDate(
        isSame: (date: LocalDate) -> Boolean,
        isBefore: (date: LocalDate) -> Boolean,
    ): HistoricalStockPrice {
        val pricesBeforeTargetDay = mutableListOf<HistoricalStockPrice>()
        val pricesAfterTargetDay = mutableListOf<HistoricalStockPrice>()
        for (price in this) {
            if (isSame(price.date)) {
                return price
            }
            if (isBefore(price.date)) {
                pricesBeforeTargetDay.add(price)
            } else {
                pricesAfterTargetDay.add(price)
            }
        }
        return pricesBeforeTargetDay.sortedBy { it.date }.maxByOrNull { it.date }
            ?: pricesAfterTargetDay.sortedBy { it.date }.minByOrNull { it.date }
            ?: throw IllegalStateException("can not find price")
    }

    private fun List<HistoricalStockPrice>.groupByYear() = this.groupBy { it.date.year }
    private fun List<HistoricalStockPrice>.groupByMonth() = this.groupBy { it.date.monthValue }
    private fun List<HistoricalStockPrice>.groupByWeek() = this.groupBy { it.date.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear()) }
}

fun main() {
    println(LocalDate.now().dayOfWeek.value)
}
