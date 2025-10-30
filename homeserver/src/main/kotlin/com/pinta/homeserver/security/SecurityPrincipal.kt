package com.pinta.homeserver.security

import org.springframework.security.core.authority.SimpleGrantedAuthority

data class SecurityPrincipal(
    val id: Long,
    val username: String,
    val email: String?,
    val authorities: List<SimpleGrantedAuthority>,
)
