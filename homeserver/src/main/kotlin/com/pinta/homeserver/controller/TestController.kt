package com.pinta.homeserver.controller

import com.pinta.homeserver.security.SecurityUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping("/echo")
    suspend fun check(): String {
        return SecurityUtils.getPrincipal()?.username ?: "anonymous"
    }
}