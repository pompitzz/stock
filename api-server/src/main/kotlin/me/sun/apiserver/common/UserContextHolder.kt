package me.sun.apiserver.common

import me.sun.apiserver.domain.entity.user.User
import me.sun.apiserver.infrastructure.security.JwtAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

object UserContextHolder {
    fun getUser(): User? {
        val authentication = SecurityContextHolder.getContext()?.authentication
        if (authentication is JwtAuthenticationToken) {
            return authentication.principal
        }
        return null
    }
}
