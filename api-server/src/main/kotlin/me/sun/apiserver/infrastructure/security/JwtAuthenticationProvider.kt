package me.sun.apiserver.infrastructure.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

class JwtAuthenticationProvider(
    private val jwtTokenHelper: JwtTokenHelper,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val jwtToken = (authentication as JwtAuthenticationToken).credentials
        val uesrId = validateAndGetUserId(jwtToken)
        TODO("userId 기반으로 user를 가져와서 auth에 세팅")
    }

    private fun validateAndGetUserId(jwtToken: String) = jwtTokenHelper.validateAndGetSubject(jwtToken)

    override fun supports(authentication: Class<*>): Boolean {
        return JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
