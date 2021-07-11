package me.sun.apiserver.application.stock

import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

internal class HistoricalStockPriceSelectorTest {

    private val selector: HistoricalStockPriceSelector = HistoricalStockPriceSelector()

    @Test
    fun `selectBySelectionCycle return same price with param prices when prices size is 1`() {
        // given
        val prices = listOf(
            createPrice(LocalDate.of(2021, 1, 1))
        )

        // when
        val resultForDay = selector.selectBySelectionCycle(prices, StockSearchSelectionCycle.DAY)
        val resultForWeek = selector.selectBySelectionCycle(prices, StockSearchSelectionCycle.WEEK)
        val resultForMonth = selector.selectBySelectionCycle(prices, StockSearchSelectionCycle.MONTH)

        // then
        assertThat(resultForDay.size).isEqualTo(1)
        assertThat(resultForWeek.size).isEqualTo(1)
        assertThat(resultForMonth.size).isEqualTo(1)

        assertThat(resultForDay[0]).isEqualTo(prices[0])
        assertThat(resultForWeek[0]).isEqualTo(prices[0])
        assertThat(resultForMonth[0]).isEqualTo(prices[0])
    }

    @Test
    fun `selectBySelectionCycle when SelectionType is WEEK`() {
        // given
        val prices = listOf(
            createPrice(LocalDate.of(2021, 7, 12)), // peek
            createPrice(LocalDate.of(2021, 7, 4)),  // peek
            createPrice(LocalDate.of(2021, 7, 6)),
            createPrice(LocalDate.of(2021, 7, 7)),
            createPrice(LocalDate.of(2021, 7, 8)),

            createPrice(LocalDate.of(2020, 12, 14)), // peek
            createPrice(LocalDate.of(2020, 12, 15)),
            createPrice(LocalDate.of(2020, 12, 29)), // peek
        )

        // when
        val resultForWeek = selector.selectBySelectionCycle(prices, StockSearchSelectionCycle.WEEK)

        // then
        assertThat(resultForWeek.size).isEqualTo(4)
        assertThat(resultForWeek[0].date).isEqualTo(LocalDate.of(2020, 12, 14))
        assertThat(resultForWeek[1].date).isEqualTo(LocalDate.of(2020, 12, 29))
        assertThat(resultForWeek[2].date).isEqualTo(LocalDate.of(2021, 7, 4))
        assertThat(resultForWeek[3].date).isEqualTo(LocalDate.of(2021, 7, 12))
    }

    @Test
    fun `selectBySelectionCycle when SelectionType is MONTH`() {
        // given
        val prices = listOf(
            createPrice(LocalDate.of(2021, 7, 12)), // peek
            createPrice(LocalDate.of(2021, 7, 4)),
            createPrice(LocalDate.of(2021, 7, 8)),

            createPrice(LocalDate.of(2019, 3, 12)), // peek
            createPrice(LocalDate.of(2019, 3, 15)),

            createPrice(LocalDate.of(2019, 5, 11)), // peek
            createPrice(LocalDate.of(2019, 5, 13)),

            createPrice(LocalDate.of(2020, 12, 14)), // peek
            createPrice(LocalDate.of(2020, 12, 15)),
        )

        // when
        val resultForWeek = selector.selectBySelectionCycle(prices, StockSearchSelectionCycle.MONTH)

        // then
        assertThat(resultForWeek.size).isEqualTo(4)
        assertThat(resultForWeek[0].date).isEqualTo(LocalDate.of(2019, 3, 12))
        assertThat(resultForWeek[1].date).isEqualTo(LocalDate.of(2019, 5, 11))
        assertThat(resultForWeek[2].date).isEqualTo(LocalDate.of(2020, 12, 14))
        assertThat(resultForWeek[3].date).isEqualTo(LocalDate.of(2021, 7, 12))
    }

    private fun createPrice(date: LocalDate) =
        HistoricalStockPrice(
            stockId = 1L,
            date = date,
            open = BigDecimal.ZERO,
            high = BigDecimal.ZERO,
            low = BigDecimal.ZERO,
            close = BigDecimal.ZERO,
            volume = BigDecimal.ZERO,
        )
}
