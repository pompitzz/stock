package me.sun.apiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ApiServerApplication {
//    @Bean
//    fun myRunner(tokenProperties: TokenProperties): ApplicationRunner = ApplicationRunner {
//        print("tokenProperties: ${tokenProperties.yahooFinanceKey}")
//    }
}


fun main(args: Array<String>) {
    runApplication<ApiServerApplication>(*args)
}

