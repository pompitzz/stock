package me.sun.apiserver.api.auth

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token")
class TokenController {
    @GetMapping("/validate")
    fun validateToken() = "success"
}
