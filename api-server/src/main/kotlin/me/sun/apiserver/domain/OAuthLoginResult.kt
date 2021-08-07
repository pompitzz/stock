package me.sun.apiserver.domain

import java.time.LocalDateTime

data class OAuthLoginResult(
    val accessToken: String,
    val accessTokenExpiryTime: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiryTime: LocalDateTime,
    val oAuthServiceType: OAuthServiceType = OAuthServiceType.KAKAO,
    val oAuthServiceUserId: Long,
    val userName: String,
)

enum class OAuthServiceType {
    KAKAO
}
