package me.sun.apiserver.infrastructure.security

import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class JwtAuthenticationConverter : AuthenticationConverter {

    companion object {
        private const val BEARER = "Bearer "
    }

    override fun convert(request: HttpServletRequest): Authentication {
        val token = resolveToken(request)
        return JwtAuthenticationToken(token = token)
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
        if (!authorization.startsWith(BEARER)) throw IllegalArgumentException("AUTHORIZATION header should start with \"Bearer\" but is $authorization")
        return authorization.substring(BEARER.length)
    }
}
