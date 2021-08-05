package me.sun.apiserver.infrastructure.security

import me.sun.apiserver.common.logger
import org.springframework.http.HttpHeaders
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.util.ObjectUtils
import javax.servlet.http.HttpServletRequest

class AuthRequestMatcher : RequestMatcher {

    companion object {
        private val LOGIN_REQUEST_MATCHER = AntPathRequestMatcher("/login/**")
    }

    private val log = logger<AuthRequestMatcher>()

    override fun matches(request: HttpServletRequest): Boolean {
        if (LOGIN_REQUEST_MATCHER.matches(request)) return false
        if (isEmptyAuthHeader(request)) {
            log.info("AUTHORIZATION is empty. pass authentication")
            return false
        }
        return true
    }

    private fun isEmptyAuthHeader(request: HttpServletRequest): Boolean {
        return ObjectUtils.isEmpty(request.getHeader(HttpHeaders.AUTHORIZATION))
    }
}
