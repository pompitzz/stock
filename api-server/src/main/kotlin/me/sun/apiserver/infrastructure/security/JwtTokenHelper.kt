package me.sun.apiserver.infrastructure.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import me.sun.apiserver.application.auth.TokenProvider
import me.sun.apiserver.properties.JwtProperties
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtTokenHelper(
    private val jwtProperties: JwtProperties,
) : TokenProvider {
    override fun createToken(userId: String): String {
        val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(now.plusMinutes(jwtProperties.expiryMinutes).toInstant()))
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .compact()
    }

    fun validateAndGetSubject(token: String): String {
        val subject = Jwts.parser()
            .setSigningKey(jwtProperties.secretKey)
            .parseClaimsJws(token)
            .body
            .subject
        if (ObjectUtils.isEmpty(subject)) throw JwtException("subject should not be empty.")
        return subject
    }
}
