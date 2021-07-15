package me.sun.apiserver.infrastructure.yahoofinace.stockprice

import com.fasterxml.jackson.module.kotlin.readValue
import me.sun.apiserver.ResourceUtils
import me.sun.apiserver.common.OBJECT_MAPPER
import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.infrastructure.yahoofinace.YahooFinanceProfile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

internal class YahooFinanceProfileTest {
    @Test
    fun `toStockPrice by json file`() {
        // given
        val file = ResourceUtils.getFile("/json/get-profile-AAPL.json")

        // when
        val profile = OBJECT_MAPPER.readValue<YahooFinanceProfile>(file)
        val stockPrice = profile.toStockPrice()

        // then
        assertThat(stockPrice.currency).isEqualTo(Currency.USD)
        assertThat(stockPrice.date).isEqualTo(LocalDate.of(2021, 7, 14))
        assertThat(stockPrice.price).isEqualTo(BigDecimal.valueOf(149.15))
    }
}
