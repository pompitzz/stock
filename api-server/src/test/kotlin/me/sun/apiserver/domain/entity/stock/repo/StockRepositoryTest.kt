package me.sun.apiserver.domain.entity.stock.repo

import me.sun.apiserver.MyDataJpaTest
import me.sun.apiserver.domain.OAuthServiceType
import me.sun.apiserver.domain.entity.Currency
import me.sun.apiserver.domain.entity.StockPrice
import me.sun.apiserver.domain.entity.stock.Stock
import me.sun.apiserver.domain.entity.user.User
import me.sun.apiserver.domain.entity.user.UserRole
import me.sun.apiserver.domain.entity.user.repo.UserRepository
import me.sun.apiserver.domain.entity.userintereststock.UserInterestStock
import me.sun.apiserver.domain.entity.userintereststock.repo.UserInterestStockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@MyDataJpaTest
internal class StockRepositoryTest {

    @Autowired
    lateinit var stockRepository: StockRepository

    @Autowired
    lateinit var userInterestStockRepository: UserInterestStockRepository

    @Autowired
    lateinit var userRepository: UserRepository

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


    @Test
    fun `save does not throw exception when entity does not violate unique constraints2`() {
        // given
        userRepository.save(createUser(id = 1L))

        stockRepository.save(createStock(id = 1L, name = "stock1", symbol = "symbol1"))
        stockRepository.save(createStock(id = 2L, name = "stock2", symbol = "symbol2"))
        stockRepository.save(createStock(id = 3L, name = "stock3", symbol = "symbol3"))

        userInterestStockRepository.save(UserInterestStock(userId = 1, stockId = 1))
        userInterestStockRepository.save(UserInterestStock(userId = 1, stockId = 3))

        // when
        val stocks = stockRepository.findInterestStocksByUserId(1L)

        // then
        assertThat(stocks.size).isEqualTo(2)
        assertThat(stocks[0].name).isEqualTo("stock1")
        assertThat(stocks[1].name).isEqualTo("stock3")
    }

    fun createStock(symbol: String = "symbol", name: String = "name", id: Long = 0) = Stock(
        id = id,
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

    fun createUser(id: Long = 0) = User(
        id = id,
        accessToken = "accessToken",
        accessTokenExpiryTime = LocalDateTime.now().plusDays(1),
        refreshToken = "refreshToken",
        refreshTokenExpiryTime = LocalDateTime.now().plusDays(1),
        oauthServiceType = OAuthServiceType.KAKAO,
        oauthServiceUserId = 1234,
        userName = "userName",
        role = UserRole.USER,
    )
}
