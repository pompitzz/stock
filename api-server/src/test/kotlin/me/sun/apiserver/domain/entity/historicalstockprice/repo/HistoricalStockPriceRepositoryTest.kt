package me.sun.apiserver.domain.entity.historicalstockprice.repo

import me.sun.apiserver.MyDataJpaTest
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

@MyDataJpaTest
internal class HistoricalStockPriceRepositoryTest {

    @Autowired
    lateinit var historicalStockPriceRepository: HistoricalStockPriceRepository

    @Test
    fun `findAllAfterOrEqualToTargetDate returns histories after targetDate`() {
        // given
        val stockId = 1L
        historicalStockPriceRepository.saveAll(
            listOf(
                createHistory(LocalDate.of(2021, 6, 1), stockId),
                createHistory(LocalDate.of(2021, 6, 2), stockId),
                createHistory(LocalDate.of(2021, 6, 3), stockId)
            )
        )

        // when
        val historicalStockPrices =
            historicalStockPriceRepository.findAllAfterOrEqualToTargetDate(stockId, LocalDate.of(2021, 6, 2))

        // then
        assertThat(historicalStockPrices.size).isEqualTo(2)
        assertThat(historicalStockPrices[0].date).isEqualTo(LocalDate.of(2021, 6, 2))
        assertThat(historicalStockPrices[1].date).isEqualTo(LocalDate.of(2021, 6, 3))
    }

    fun createHistory(date: LocalDate, stockId: Long) = HistoricalStockPrice(
        stockId = stockId,
        date = date,
        open = BigDecimal(ThreadLocalRandom.current().nextDouble(100000.0)),
        high = BigDecimal(ThreadLocalRandom.current().nextDouble(100000.0)),
        low = BigDecimal(ThreadLocalRandom.current().nextDouble(100000.0)),
        close = BigDecimal(ThreadLocalRandom.current().nextDouble(100000.0)),
        volume = BigDecimal(ThreadLocalRandom.current().nextDouble(100000.0)),
    )
}
