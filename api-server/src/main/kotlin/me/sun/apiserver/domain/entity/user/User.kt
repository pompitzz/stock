package me.sun.apiserver.domain.entity.user

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
    val accessToken: String,
    @Column(nullable = false)
    val accessTokenExpiryTime: LocalDateTime,
    @Column(nullable = false)
    val refreshToken: String,
    @Column(nullable = false)
    val refreshTokenExpiryTime: LocalDateTime,
    @Column(nullable = false)
    val oauthServiceType: OAuthServiceType,
    @Column(nullable = false)
    val oauthServiceUserId: Long,
    @Column(nullable = false)
    val userName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.USER,
)

enum class UserRole {
    USER, ADMIN
}
