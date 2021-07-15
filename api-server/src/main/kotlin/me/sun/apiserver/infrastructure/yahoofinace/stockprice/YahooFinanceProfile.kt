package me.sun.apiserver.infrastructure.yahoofinace

import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.StockPrice
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

data class YahooFinanceProfile(
    val price: YahooFinancePrice,
    val quoteType: YahooFinanceQuoteType,
    val symbol: String,
) {
    fun toStockPrice() = StockPrice(
        date = getRegularMarketDate(),
        price = price.getPrice(),
        currency = price.currency
    )

    private fun getRegularMarketDate(): LocalDate {
        val instant = Instant.ofEpochSecond(price.regularMarketTime)
        val zoneId = ZoneId.of(quoteType.exchangeTimezoneName)
        return ZonedDateTime.ofInstant(instant, zoneId).toLocalDate()
    }
}

data class YahooFinancePrice(
    val currency: Currency,
    val regularMarketTime: Long,
    val regularMarketPrice: YahooFinanceNumericValue,
) {
    fun getPrice() = regularMarketPrice.raw
}

data class YahooFinanceNumericValue(
    val raw: BigDecimal,
    val fmt: String,
)

data class YahooFinanceQuoteType(
    val exchangeTimezoneName: String,
)
