package me.sun.apiserver.application.stock

import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.StockPrice
import me.sun.apiserver.domain.entity.stock.Stock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

internal class StockSyncDeciderTest {

    var stockSyncDecider: StockSyncDecider = StockSyncDecider()

    @Test
    fun `isSyncTarget should return false when lastSyncedAt is equal to now`() {
        fun assert(zoneId: ZoneId) {
            for (i in 0..23) {
                val now = ZonedDateTime.now(zoneId).toLocalDate()
                val lastSyncedAt = ZonedDateTime.of(now.year, now.monthValue, now.dayOfMonth, i, 59, 59, 0, zoneId)
                val stock = createStock(lastSyncedAt)
                assertThat(stockSyncDecider.isSyncTarget(stock)).isFalse
            }
        }

        assert(ZoneId.of("Asia/Seoul"))
        assert(ZoneId.of("America/New_York"))
    }

    private fun createStock(lastSyncedAt: ZonedDateTime) = Stock(
        stockPrice = StockPrice(LocalDate.now(), BigDecimal.ONE, Currency.USD),
        symbol = "symbol",
        exchange = "exchange",
        name = "name",
        market = "market",
        timeZone = lastSyncedAt.zone.toString(),
        sector = "sector",
        state = "state",
        industry = "industry",
        website = "website",
        logoUrl = "logoUrl",
        lastSyncedAt = lastSyncedAt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    )
}
