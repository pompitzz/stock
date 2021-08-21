package me.sun.apiserver.domain.entity.stock

import me.sun.apiserver.common.toZonedDateTime
import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.StockPrice
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.*

private val NEW_YORK_CLOSE_TIME = LocalTime.of(16, 0)
private val SEOUL_CLOSE_TIME = LocalTime.of(15, 30)

@Entity
class Stock(
    @Id
    @Column(name = "stock_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Embedded
    var stockPrice: StockPrice,
    @Column(nullable = false, unique = true)
    val symbol: String,
    @Column(nullable = false)
    val exchange: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val market: String,
    @Column(nullable = false)
    val timeZone: String,
    val sector: String?,
    val state: String?,
    val industry: String?,
    val website: String?,
    val logoUrl: String?,
    @Column(nullable = false)
    var lastSyncedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun syncStockPrice(stockPrice: StockPrice) {
        this.stockPrice = stockPrice
        this.lastSyncedAt = LocalDateTime.now()
    }

    fun isMargetOpening(zonedDateTime: ZonedDateTime): Boolean {
        val time = zonedDateTime.withZoneSameInstant(ZoneId.of(timeZone)).toLocalTime().minusMinutes(1)
        if (stockPrice.currency == Currency.USD) {
            return time.isBefore(NEW_YORK_CLOSE_TIME)
        }
        return time.isBefore(SEOUL_CLOSE_TIME)
    }

    fun getTimeZoneId(): ZoneId = ZoneId.of(timeZone)

    fun getZonedLastSyncedAt(): ZonedDateTime = lastSyncedAt.toZonedDateTime().withZoneSameInstant(getTimeZoneId())
}
