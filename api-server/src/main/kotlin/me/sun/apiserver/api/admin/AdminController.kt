package me.sun.apiserver.api.admin

import me.sun.apiserver.common.UserContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/admin")
@RestController
class AdminController {

    @GetMapping("/profile")
    fun profile(): String {
        val user = UserContextHolder.getUser()
        return user?.userName ?: "not found user"
    }
}
