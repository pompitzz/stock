package me.sun.apiserver.infrastructure.yahoofinace.historicalstockprice

import me.sun.apiserver.infrastructure.yahoofinace.stockprice.YahooFinanceHistoricalStockPriceProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class YahooFinanceHistoricalStockPriceProviderTest {
    @Autowired
    lateinit var provider: YahooFinanceHistoricalStockPriceProvider

    @Test
    fun `getHistoricalStockPrices for AAPL`() {
        // when
        val historicalStockPrices = provider.getHistoricalStockPrices("AAPL", 1)

        // then
        assertThat(historicalStockPrices).isNotEmpty
    }
}
