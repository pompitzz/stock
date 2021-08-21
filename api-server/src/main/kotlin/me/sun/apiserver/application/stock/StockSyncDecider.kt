package me.sun.apiserver.application.stock

import me.sun.apiserver.common.isWeekend
import me.sun.apiserver.common.weekOfYear
import me.sun.apiserver.domain.entity.stock.Stock
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.ZonedDateTime

@Component
class StockSyncDecider {
    // TODO: improve algorithm
    // TODO: add flag to sync only target stock
    fun isSyncTarget(stock: Stock): Boolean {
        val zonedNow = ZonedDateTime.now().withZoneSameInstant(stock.getTimeZoneId())

        if (stock.getZonedLastSyncedAt().isSameDateWithNow(zonedNow)) return false

        if (zonedNow.dayOfWeek.isWeekend()) {
            return decideSyncTargetWhenWeekend(stock, zonedNow)
        }
        return !stock.isMargetOpening(zonedNow)
    }

    private fun decideSyncTargetWhenWeekend(stock: Stock, zonedNow: ZonedDateTime): Boolean {
        val zonedLastSyncedAt = stock.getZonedLastSyncedAt()

        if (zonedNow.year > zonedLastSyncedAt.year) return true

        if (zonedNow.weekOfYear() > zonedLastSyncedAt.weekOfYear()) return true

        return zonedLastSyncedAt.dayOfWeek != DayOfWeek.FRIDAY
    }

    private fun ZonedDateTime.isSameDateWithNow(zonedNow: ZonedDateTime) = zonedNow.toLocalDate().isEqual(this.toLocalDate())
}
