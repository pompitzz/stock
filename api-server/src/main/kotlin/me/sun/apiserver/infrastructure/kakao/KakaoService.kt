package me.sun.apiserver.infrastructure.kakao

import me.sun.apiserver.ApiFailureException
import me.sun.apiserver.application.LoginRequest
import me.sun.apiserver.domain.OAuthLoginResult
import me.sun.apiserver.domain.OAuthService
import me.sun.apiserver.infrastructure.HttpFormUrlencodedRequestInfo
import me.sun.apiserver.infrastructure.HttpJsonRequestInfo
import me.sun.apiserver.infrastructure.HttpRequester
import me.sun.apiserver.properties.KakaoProperties
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service

@Service
class KakaoService(
    private val kakaoProperties: KakaoProperties,
) : OAuthService {
    companion object {
        const val TOKEN_API_URL = "https://kauth.kakao.com/oauth/token"
        const val GET_PROFILE_API_URL = "https://kapi.kakao.com/v2/user/me"
    }

    override fun login(loginRequest: LoginRequest): OAuthLoginResult {
        val kakaoToken = issueToken(loginRequest)
        val profile = getProfile(kakaoToken.access_token)
        return OAuthLoginResult(
            accessToken = kakaoToken.access_token,
            accessTokenExpirySeconds = kakaoToken.expires_in,
            refreshToken = kakaoToken.refresh_token,
            refreshTokenExpirySeconds = kakaoToken.refresh_token_expires_in,
            oAuthServiceUserId = profile.id,
            userName = profile.kakao_account.profile.nickname
        )
    }

    private fun issueToken(loginRequest: LoginRequest): KakaoToken {
        val requestInfo = HttpFormUrlencodedRequestInfo(
            requestUrl = TOKEN_API_URL,
            requestMethod = HttpMethod.POST,
            requestBody = mapOf(
                "grant_type" to "authorization_code",
                "client_id" to kakaoProperties.clientId,
                "code" to loginRequest.code,
                "redirect_uri" to loginRequest.redirectUrl
            )
        )

        return HttpRequester.requestWithFormUrlencoded<KakaoToken>(requestInfo).body
            ?: throw ApiFailureException("Fail issue kakao token")
    }

    private fun getProfile(accessToken: String): KakaoUserProfile {
        val requestInfo = HttpJsonRequestInfo(
            accessToken = accessToken,
            requestUrl = GET_PROFILE_API_URL,
            requestMethod = HttpMethod.POST,
        )

        return HttpRequester.requestWithJson<KakaoUserProfile>(requestInfo).body
            ?: throw ApiFailureException("Fail get kakao user profile")
    }
}
