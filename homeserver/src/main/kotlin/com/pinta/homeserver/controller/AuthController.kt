package com.pinta.homeserver.controller

import com.pinta.homeserver.security.SecurityUtils
import com.pinta.homeserver.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody credentials: LoginCredentials
    ): String {
        return authService.login(credentials.email, credentials.password)
    }

    @GetMapping("/check")
    suspend fun check(): String {
        return SecurityUtils.getPrincipal()?.username ?: "anonymous"
    }

    data class LoginCredentials(val email: String, val password: String)
}