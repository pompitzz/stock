package me.sun.apiserver.api.login

import me.sun.apiserver.application.LoginRequest
import me.sun.apiserver.application.LoginService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val loginService: LoginService
) {
    @GetMapping("/kakao")
    fun kakaoLogin(
        @RequestParam code: String,
        @RequestParam redirectUrl: String,
    ): JwtTokenResponse {
        val token = loginService.login(LoginRequest(code, redirectUrl))
        return JwtTokenResponse(token)
    }

    class JwtTokenResponse(
        val token: String,
    )
}
