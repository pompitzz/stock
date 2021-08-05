package me.sun.apiserver.domain

data class OAuthLoginResult(
    val accessToken: String,
    val accessTokenExpirySeconds: Int,
    val refreshToken: String,
    val refreshTokenExpirySeconds: Int,
    val oAuthServiceType: OAuthServiceType = OAuthServiceType.KAKAO,
    val oAuthServiceUserId: Long,
    val userName: String,
)

enum class OAuthServiceType {
    KAKAO
}
