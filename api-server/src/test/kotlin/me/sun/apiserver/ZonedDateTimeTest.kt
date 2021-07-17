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

    @Test
    fun timeTest() {
        println(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")))
        println(ZonedDateTime.now().plusHours(5).withZoneSameInstant(ZoneId.of("America/New_York")))
    }

    @Test
    fun `dayOfWeek`() {
        println(ZonedDateTime.now().minusHours(10).dayOfWeek)
        println(ZonedDateTime.now().minusHours(10).withZoneSameInstant(ZoneId.of("America/New_York")).dayOfWeek)
    }

    private fun printZonedDateTime(newYorkMarketTime: ZonedDateTime) {
        println(newYorkMarketTime)
        println(newYorkMarketTime.toLocalDate())
        println(newYorkMarketTime.toLocalTime())
        println(newYorkMarketTime.withZoneSameInstant(ZoneId.systemDefault()))
        println(newYorkMarketTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate())
        println(newYorkMarketTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime())
    }
}
