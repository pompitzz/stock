package me.sun.apiserver.infrastructure.yahoofinace.stockprice

import me.sun.apiserver.domain.entity.Currency
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class YahooFinanceStockPriceProviderTest {
    @Autowired
    lateinit var yahooFinanceStockPriceProvider: YahooFinanceStockPriceProvider

    @Test
    fun `getStockPrice for AAPL`() {
        // when
        val stockPrice = yahooFinanceStockPriceProvider.getStockPrice("AAPL")

        // then
        assertThat(stockPrice.currency).isEqualTo(Currency.USD)
    }
}
