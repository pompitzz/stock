package me.sun.apiserver

import me.sun.apiserver.domain.OAuthLoginResult
import me.sun.apiserver.domain.OAuthServiceType
import me.sun.apiserver.domain.entity.user.User
import java.time.LocalDateTime

fun createLoginResult(oauthServiceUserId: Long = 1, userName: String = "userName") = OAuthLoginResult(
    accessToken = "accessToken",
    accessTokenExpiryTime = LocalDateTime.now(),
    refreshToken = "refreshToken",
    refreshTokenExpiryTime = LocalDateTime.now(),
    oauthServiceType = OAuthServiceType.KAKAO,
    oauthServiceUserId = oauthServiceUserId,
    userName = userName,
)

fun createUser(oauthServiceUserId: Long = 1, userName: String = "userName") = User(
    accessToken = "accessToken",
    accessTokenExpiryTime = LocalDateTime.now(),
    refreshToken = "refreshToken",
    refreshTokenExpiryTime = LocalDateTime.now(),
    oauthServiceType = OAuthServiceType.KAKAO,
    oauthServiceUserId = oauthServiceUserId,
    userName = userName,
)
