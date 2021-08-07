package me.sun.apiserver.domain.entity.user

import me.sun.apiserver.domain.OAuthLoginResult
import me.sun.apiserver.domain.OAuthServiceType
import java.time.LocalDateTime
import javax.persistence.*

@Table(indexes = [Index(name = "oauth", columnList = "oauthServiceUserId, oauthServiceType", unique = true)])
@Entity
class User(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    var accessToken: String,
    @Column(nullable = false)
    var accessTokenExpiryTime: LocalDateTime,
    @Column(nullable = false)
    var refreshToken: String,
    @Column(nullable = false)
    var refreshTokenExpiryTime: LocalDateTime,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var oauthServiceType: OAuthServiceType,
    @Column(nullable = false)
    var oauthServiceUserId: Long,
    @Column(nullable = false)
    var userName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,
) {
    fun update(loginResult: OAuthLoginResult) {
        accessToken = loginResult.accessToken
        accessTokenExpiryTime = loginResult.accessTokenExpiryTime
        refreshToken = loginResult.refreshToken
        refreshTokenExpiryTime = loginResult.refreshTokenExpiryTime
        oauthServiceType = loginResult.oauthServiceType
        oauthServiceUserId = loginResult.oauthServiceUserId
        userName = loginResult.userName
    }
}

enum class UserRole {
    USER,
    ADMIN;

    fun getRoleName(): String = "ROLE_${this.name}"
}
