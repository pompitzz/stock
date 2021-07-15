package me.sun.apiserver

import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

internal class ZonedDateTimeTest {
//    "exchangeTimezoneName": "America/New_York",
//    "regularMarketTime": 1626292803,
//    "exchange": "NMS",

//    "exchange": "KSC",
//    "regularMarketTime": 1624343406,
//    "exchangeTimezoneName": "Asia/Seoul",

    @Test
    fun `newyork`() {
        printZonedDateTime(ZonedDateTime.ofInstant(Instant.ofEpochSecond(1626292803), ZoneId.of("America/New_York")))
        printZonedDateTime(ZonedDateTime.ofInstant(Instant.ofEpochSecond(1624343406), ZoneId.of("Asia/Seoul")))
    }

    private fun printZonedDateTime(newYorkMarketTime: ZonedDateTime) {
        println(newYorkMarketTime)
        println(newYorkMarketTime.toLocalDate())
    }
}
