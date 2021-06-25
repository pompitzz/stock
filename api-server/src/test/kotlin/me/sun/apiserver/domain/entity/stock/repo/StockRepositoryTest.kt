package me.sun.apiserver.domain.entity.stock.repo

import me.sun.apiserver.MyDataJpaTest
import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.StockPrice
import me.sun.apiserver.domain.entity.stock.Stock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.LocalDate

@MyDataJpaTest
internal class StockRepositoryTest {

    @Autowired
    lateinit var stockRepository: StockRepository

    @Test
    fun `search returns stockPage when symbol or name contains query`() {
        // given
        val query = "Ap"
        stockRepository.saveAll(
            listOf(
                createStock("ZAPA", "name"),
                createStock("APPL", "name"),
                createStock("SEARCHED1", "Apple"),
                createStock("SEARCHED2", "zzAPzz"),
                createStock("SEARCHED3", "zzaP"),
                createStock("UNSEARCHED1", "Pla"),
                createStock("UNSEARCHED2", "Pa"),
            )
        )

        // when
        val stocks = stockRepository.search(query, PageRequest.of(0, 10)).content

        // then
        assertThat(stocks.size).isEqualTo(5)
        assertThat(stocks)
            .extracting("symbol")
            .containsExactly("ZAPA", "APPL", "SEARCHED1", "SEARCHED2", "SEARCHED3")
    }

    fun createStock(symbol: String = "symbol", name: String = "name") = Stock(
        stockPrice = StockPrice(LocalDate.now(), BigDecimal.ONE, Currency.USD),
        symbol = symbol,
        exchange = "exchange",
        name = name,
        market = "market",
        timeZone = "timeZone",
        sector = "sector",
        state = "state",
        industry = "industry",
        website = "website",
        logoUrl = "logoUrl",
    )
}
