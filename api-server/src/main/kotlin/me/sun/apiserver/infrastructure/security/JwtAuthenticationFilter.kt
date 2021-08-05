package me.sun.apiserver.infrastructure.security

import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(requestMatcher: RequestMatcher) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    companion object {
        private const val BEARER = "Bearer "
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token = resolveToken(request)
        val jwtAuthenticationToken = JwtAuthenticationToken(token)
        return authenticationManager.authenticate(jwtAuthenticationToken)
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
        if (!authorization.startsWith(BEARER)) throw IllegalArgumentException("AUTHORIZATION header should start with \"Bearer\" but is $authorization")
        return authorization.substring(BEARER.length)
    }
}
