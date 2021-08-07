package me.sun.apiserver.domain.service

import me.sun.apiserver.createLoginResult
import me.sun.apiserver.createUser
import me.sun.apiserver.domain.entity.user.repo.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `login updates user when user is exist`() {
        // given
        userRepository.save(createUser(1L, "user1"))

        // when
        val user = userService.login(createLoginResult(1L, "newUser1"))

        // then
        assertThat(user.userName).isEqualTo("newUser1")
    }

    @Test
    fun `login add user when user is not exist`() {
        // when
        val user = userService.login(createLoginResult(1L, "user1"))

        // then
        assertThat(user.userName).isEqualTo("user1")
    }
}
