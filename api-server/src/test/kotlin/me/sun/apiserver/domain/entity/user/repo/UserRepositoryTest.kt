package me.sun.apiserver.domain.entity.user.repo

import me.sun.apiserver.MyDataJpaTest
import me.sun.apiserver.createUser
import me.sun.apiserver.domain.OAuthServiceType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

@MyDataJpaTest
internal class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `save throw exception when same oauth user save twice`() {
        userRepository.save(createUser())

        assertThatThrownBy {
            userRepository.save(createUser())
        }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun `findByOauthServiceUserIdAndOauthServiceType returns empty user when user is empty`() {
        // when
        val user = userRepository.findByOauthServiceUserIdAndOauthServiceType(1L, OAuthServiceType.KAKAO)

        // then
        assertThat(user).isNull()
    }

    @Test
    fun `findByOauthServiceUserIdAndOauthServiceType returns matched user`() {
        // given
        userRepository.save(createUser(1L, "user1"))
        userRepository.save(createUser(2L, "user2"))
        userRepository.save(createUser(3L, "user3"))

        // when
        val user = userRepository.findByOauthServiceUserIdAndOauthServiceType(2L, OAuthServiceType.KAKAO)!!

        // then
        assertThat(user.userName).isEqualTo("user2")
    }
}
