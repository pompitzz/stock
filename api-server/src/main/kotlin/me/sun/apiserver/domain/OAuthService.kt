package me.sun.apiserver.domain

import me.sun.apiserver.application.LoginRequest

interface OAuthService {
    fun login(loginRequest: LoginRequest): OAuthLoginResult
}
