package me.sun.apiserver.infrastructure.yahoofinace.stockprice

import me.sun.apiserver.ApiFailureException
import me.sun.apiserver.domain.entity.historicalstockprice.HistoricalStockPrice
import me.sun.apiserver.domain.service.provider.HistoricalStockPricesProvider
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

private const val URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data"

@Component
class YahooFinanceHistoricalStockPriceProvider(
    private val yahooFinanceRestTemplate: RestTemplate,
) : HistoricalStockPricesProvider {
    override fun getHistoricalStockPrices(symbol: String, stockId: Long): List<HistoricalStockPrice> {
        val historicalData: YahooFinanceHistoricalData = yahooFinanceRestTemplate.getForEntity<YahooFinanceHistoricalData>("${URL}?symbol=${symbol}")
            .body ?: throw ApiFailureException("cannot find historical data called with yahoo finance get-historical-data api")
        val offsetSeconds = historicalData.getOffsetSeconds()
        return historicalData.prices
            .asSequence()
            .filter { it.open != null }
            .filter { it.high != null }
            .filter { it.low != null }
            .filter { it.close != null }
            .filter { it.volume != null }
            .filter { it.adjclose != null }
            .map { it.toHistoricalStockPrice(stockId, offsetSeconds) }
            .toList()
    }
}

data class YahooFinanceHistoricalData(
    val prices: List<YahooFinanceHistoricalStockPrice>,
    val timeZone: YahooFinanceHistoricalTimeZone,
) {
    fun getOffsetSeconds() = timeZone.gmtOffset
}

data class YahooFinanceHistoricalTimeZone(
    val gmtOffset: Int,
)

data class YahooFinanceHistoricalStockPrice(
    val date: Long,
    val open: BigDecimal?,
    val high: BigDecimal?,
    val low: BigDecimal?,
    val close: BigDecimal?,
    val volume: BigDecimal?,
    val adjclose: BigDecimal?,
) {
    fun toHistoricalStockPrice(stockId: Long, offsetSeconds: Int) = HistoricalStockPrice(
        stockId = stockId,
        date = ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneOffset.ofTotalSeconds(offsetSeconds)).toLocalDate(),
        open = open!!,
        high = high!!,
        low = low!!,
        close = close!!,
        volume = volume!!,
    )
}
