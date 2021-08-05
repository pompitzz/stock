package me.sun.apiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ApiServerApplication {
//    @Bean
//    fun myRunner(jwtTokenHelper: JwtTokenHelper): ApplicationRunner = ApplicationRunner {
//        val createToken = jwtTokenHelper.createToken("hello")
//        println(createToken)
//        val subject = jwtTokenHelper.validateAndGetSubject(createToken)
//        println(subject)
//    }
}


fun main(args: Array<String>) {
    runApplication<ApiServerApplication>(*args)
}

