package me.sun.apiserver.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "token")
class TokenProperties(
    val yahooFinanceKey: String,
)
