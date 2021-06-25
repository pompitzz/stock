package me.sun.apiserver.domain.entity.userintereststock

import me.sun.apiserver.MyDataJpaTest
import me.sun.apiserver.domain.entity.userintereststock.repo.UserInterestStockRepository
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

@MyDataJpaTest
internal class UserInterestStockRepositoryTest {
    @Autowired
    lateinit var userInterestStockRepository: UserInterestStockRepository

    @Test
    fun `save throws exception when entity violate unique constraints`() {
        // given
        userInterestStockRepository.save(UserInterestStock(userId = 1, stockId = 1))

        // when && then
        assertThatThrownBy {
            userInterestStockRepository.save(UserInterestStock(userId = 1, stockId = 1))
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun `save does not throw exception when entity does not violate unique constraints`() {
        // given
        userInterestStockRepository.save(UserInterestStock(userId = 1, stockId = 1))

        // when && then
        userInterestStockRepository.save(UserInterestStock(userId = 1, stockId = 2))
        userInterestStockRepository.save(UserInterestStock(userId = 2, stockId = 1))
    }
}
