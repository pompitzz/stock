package me.sun.apiserver.infrastructure.yahoofinace

import me.sun.apiserver.properties.YahooFinanceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate

@Configuration
class YahooFinanceConfiguration {
    @Bean
    fun yahooFinanceRestTemplate(yahooFinanceProperties: YahooFinanceProperties): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(
            ClientHttpRequestInterceptor { request, body, execution ->
                request.headers.add("x-rapidapi-key", yahooFinanceProperties.key)
                execution.execute(request, body)
            }
        )
        return restTemplate
    }
}
