package me.sun.apiserver.domain.entity.user.repo

import me.sun.apiserver.domain.OAuthServiceType
import me.sun.apiserver.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByOauthServiceUserIdAndOauthServiceType(
        oauthServiceId: Long,
        oauthServiceType: OAuthServiceType,
    ): User?
}
