package me.sun.apiserver.domain

import me.sun.apiserver.application.LoginRequest

interface OauthService {
    fun login(loginRequest: LoginRequest): OAuthLoginResult
}
