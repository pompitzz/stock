package me.sun.apiserver.domain.service

import me.sun.apiserver.domain.OAuthLoginResult
import me.sun.apiserver.domain.entity.user.User
import me.sun.apiserver.domain.entity.user.repo.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun login(loginResult: OAuthLoginResult): User {
        val findUser = userRepository.findByOauthServiceUserIdAndOauthServiceType(loginResult.oauthServiceUserId, loginResult.oauthServiceType)
        if (findUser == null) {
            return userRepository.save(loginResult.toUserEntity())
        } else {
            findUser.update(loginResult)
            return findUser
        }
    }
}
