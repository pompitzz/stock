package me.sun.apiserver.application.user

import me.sun.apiserver.application.stock.InterestStockService
import me.sun.apiserver.domain.entity.userintereststock.repo.UserInterestStockRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class InterestStockServiceTest {

    @Autowired
    lateinit var interestStockService: InterestStockService

    @Autowired
    lateinit var userInterestStockRepository: UserInterestStockRepository

    @Test
    fun `remove throws exception when entity is not exist`() {
        // when && then
        assertThatThrownBy {
            interestStockService.delete(1L, 1L)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `remove make deleted field to true`() {
        // given
        interestStockService.add(1L, 1L)
        val interestStockBeforeRemove = userInterestStockRepository.findByUserIdAndStockId(1L, 1L)

        // when
        interestStockService.delete(1L, 1L)

        // then
        val interestStockAfterRemove = userInterestStockRepository.findByUserIdAndStockId(1L, 1L)
        assertThat(interestStockBeforeRemove?.deleted).isFalse
        assertThat(interestStockAfterRemove?.deleted).isTrue
    }

}
