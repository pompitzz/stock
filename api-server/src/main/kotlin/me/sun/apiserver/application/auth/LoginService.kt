package me.sun.apiserver.application

import me.sun.apiserver.application.auth.TokenProvider
import me.sun.apiserver.domain.OauthService
import me.sun.apiserver.domain.service.UserService
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val oauthService: OauthService,
    private val userService: UserService,
    private val tokenProvider: TokenProvider
) {
    fun login(loginRequest: LoginRequest): String {
        val oauthLoginResult = oauthService.login(loginRequest)
        val user = userService.login(oauthLoginResult)
        return tokenProvider.createToken(user.id.toString())
    }
}

data class LoginRequest(val code: String, val redirectUrl: String)
