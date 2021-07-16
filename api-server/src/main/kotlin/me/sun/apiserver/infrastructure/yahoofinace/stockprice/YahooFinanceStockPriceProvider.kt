package me.sun.apiserver.infrastructure.yahoofinace.stockprice

import me.sun.apiserver.ApiFailureException
import me.sun.apiserver.domain.entity.StockPrice
import me.sun.apiserver.domain.service.provider.StockPriceProvider
import me.sun.apiserver.infrastructure.yahoofinace.YahooFinanceProfile
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

private const val URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-profile"

@Component
class YahooFinanceStockPriceProvider(
    private val yahooFinanceRestTemplate: RestTemplate,
) : StockPriceProvider {
    override fun getStockPrice(symbol: String): StockPrice {
        val profile: ResponseEntity<YahooFinanceProfile> = yahooFinanceRestTemplate.getForEntity("${URL}?symbol=${symbol}")
        return profile.body?.toStockPrice() ?: throw ApiFailureException("cannot find profile called with yahoo finance get-profile api")
    }
}
