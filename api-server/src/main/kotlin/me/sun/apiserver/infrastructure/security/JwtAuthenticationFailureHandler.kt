package me.sun.apiserver.infrastructure.security

import me.sun.apiserver.common.logger
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFailureHandler : AuthenticationFailureHandler {

    private val log = logger<JwtAuthenticationFailureHandler>()

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(exception.message ?: "UnAuthorized")
        log.info("onAuthenticationFailure", exception)
    }
}
