package me.sun.apiserver.infrastructure.yahoofinace.historicalstockprice

import com.fasterxml.jackson.module.kotlin.readValue
import me.sun.apiserver.ResourceUtils
import me.sun.apiserver.common.OBJECT_MAPPER
import me.sun.apiserver.infrastructure.yahoofinace.stockprice.YahooFinanceHistoricalData
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

internal class YahooFinanceHistoricalDataTest {
    @Test
    fun `test historical`() {
        // given
        val file = ResourceUtils.getFile("/json/get-historical-data-AAPL.json")

        // when
        val data = OBJECT_MAPPER.readValue<YahooFinanceHistoricalData>(file)

        // then
        data.prices
            .asSequence()
            .filter { it.open != null }
            .filter { it.high != null }
            .filter { it.low != null }
            .filter { it.close != null }
            .filter { it.volume != null }
            .filter { it.adjclose != null }
            .toList()
            .forEach {
                println(
                    " ${ZonedDateTime.ofInstant(Instant.ofEpochSecond(it.date), ZoneId.of("America/New_York"))}," +
                            " ${ZonedDateTime.ofInstant(Instant.ofEpochSecond(it.date), ZoneOffset.ofTotalSeconds(data.timeZone.gmtOffset))} "
                )
            }
    }
}
