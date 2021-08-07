package me.sun.apiserver.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "yahoo-finance")
class YahooFinanceProperties(
    val key: String,
)
