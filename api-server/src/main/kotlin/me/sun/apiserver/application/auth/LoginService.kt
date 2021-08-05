package me.sun.apiserver.application

import me.sun.apiserver.application.auth.TokenProvider
import me.sun.apiserver.domain.OAuthService
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val oAuthService: OAuthService,
    private val tokenProvider: TokenProvider
) {
    fun login(loginRequest: LoginRequest): String {
        val oauthLoginResult = oAuthService.login(loginRequest)
        // TODO save in user and get user
        // TODO create (App)Token
        return ""
    }
}

data class LoginRequest(val code: String, val redirectUrl: String)
