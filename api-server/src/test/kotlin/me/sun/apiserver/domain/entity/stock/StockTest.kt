package me.sun.apiserver.domain.entity.stock

import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.StockPrice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.*
import java.time.LocalTime.of

internal class StockTest {
    @Test
    fun `isMargetOpening for America_New_York`() {
        // given
        val stock = createStock(Currency.USD, "America/New_York")
        val zoneId = ZoneId.of("America/New_York")

        // then
        assertThat(stock.isMargetOpening(zone(of(16, 1), zoneId))).isFalse
        assertThat(stock.isMargetOpening(zone(of(16, 0), zoneId))).isTrue
        assertThat(stock.isMargetOpening(zone(of(15, 59), zoneId))).isTrue
    }

    @Test
    fun `isMargetOpening for Asia_Seoul`() {
        // given
        val stock = createStock(Currency.KRW, "Asia/Seoul")
        val zoneId = ZoneId.of("Asia/Seoul")

        // then
        assertThat(stock.isMargetOpening(zone(of(15, 31), zoneId))).isFalse
        assertThat(stock.isMargetOpening(zone(of(15, 30), zoneId))).isTrue
        assertThat(stock.isMargetOpening(zone(of(15, 29), zoneId))).isTrue
    }

    fun zone(localTime: LocalTime, zoneId: ZoneId) =
        ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), localTime), zoneId)

    fun createStock(currency: Currency, timeZone: String) = Stock(
        stockPrice = StockPrice(LocalDate.now(), BigDecimal.ONE, currency),
        symbol = "symbol",
        exchange = "exchange",
        name = "name",
        market = "market",
        timeZone = timeZone,
        sector = "sector",
        state = "state",
        industry = "industry",
        website = "website",
        logoUrl = "logoUrl",
    )
}
