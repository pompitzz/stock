package me.sun.apiserver.infrastructure.yahoofinace

import me.sun.apiserver.properties.TokenProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate

@Configuration
class YahooFinanceConfiguration {
    @Bean
    fun yahooFinanceRestTemplate(tokenProperties: TokenProperties): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(
            ClientHttpRequestInterceptor { request, body, execution ->
                request.headers.add("x-rapidapi-key", tokenProperties.yahooFinanceKey)
                execution.execute(request, body)
            }
        )
        return restTemplate
    }
}
