package me.sun.apiserver.infrastructure.security

import io.jsonwebtoken.ExpiredJwtException
import me.sun.apiserver.domain.entity.user.User
import me.sun.apiserver.domain.entity.user.repo.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider(
    private val jwtTokenHelper: JwtTokenHelper,
    private val userRepository: UserRepository,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        try {
            val jwtToken = (authentication as JwtAuthenticationToken).credentials
            val userId = jwtTokenHelper.validateAndGetUserId(jwtToken)
            val user: User = userRepository.findByIdOrNull(userId.toLong()) ?: throw InternalAuthenticationServiceException("failed find user. userId: $userId")
            return JwtAuthenticationToken(user = user, token = jwtToken, roles = user.getAllRoles())
        } catch (e: ExpiredJwtException) {
            throw CredentialsExpiredException("jwt token is expired", e)
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    private fun User.getAllRoles(): Collection<GrantedAuthority> {
        return role.getPossibleRoleNames()
            .map { "ROLE_${it}" }
            .map { SimpleGrantedAuthority(it) }
            .toList()
    }
}
