package me.sun.apiserver.infrastructure.security

import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthenticationToken(
    private val token: String,
) : AbstractAuthenticationToken(null) {
    override fun getCredentials(): String = token
    override fun getPrincipal() = null
}
