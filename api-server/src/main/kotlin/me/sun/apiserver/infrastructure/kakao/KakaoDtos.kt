package me.sun.apiserver.infrastructure.kakao

data class KakaoToken(
    val token_type: String,
    val access_token: String,
    val expires_in: Long,
    val refresh_token: String,
    val refresh_token_expires_in: Long,
)

data class KakaoUserProfile(
    val id: Long,
    val kakao_account: KakaoUserAccount,
)

data class KakaoUserAccount(
    val profile: Profile,
)

data class Profile(
    val nickname: String,
)
