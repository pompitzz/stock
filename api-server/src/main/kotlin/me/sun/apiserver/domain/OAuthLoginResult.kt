package me.sun.apiserver.domain

import me.sun.apiserver.domain.entity.user.User
import java.time.LocalDateTime

data class OAuthLoginResult(
    val accessToken: String,
    val accessTokenExpiryTime: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiryTime: LocalDateTime,
    val oauthServiceType: OAuthServiceType = OAuthServiceType.KAKAO,
    val oauthServiceUserId: Long,
    val userName: String,
) {
    fun toUserEntity() = User(
        accessToken = accessToken,
        accessTokenExpiryTime = accessTokenExpiryTime,
        refreshToken = refreshToken,
        refreshTokenExpiryTime = refreshTokenExpiryTime,
        oauthServiceType = oauthServiceType,
        oauthServiceUserId = oauthServiceUserId,
        userName = userName,
    )
}

enum class OAuthServiceType {
    KAKAO
}
