package me.sun.apiserver.infrastructure.security

import me.sun.apiserver.domain.entity.user.User
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
    private val user: User? = null,
    private val token: String,
    roles: Collection<GrantedAuthority> = emptyList(),
) : AbstractAuthenticationToken(roles) {

    init {
        this.isAuthenticated = user != null
    }

    override fun getCredentials(): String = this.token
    override fun getPrincipal() = this.user
}
