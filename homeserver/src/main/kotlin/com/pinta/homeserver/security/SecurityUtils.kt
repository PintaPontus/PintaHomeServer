package com.pinta.homeserver.security

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.security.core.context.ReactiveSecurityContextHolder

class SecurityUtils {
    companion object {
        suspend fun getPrincipal(): SecurityPrincipal? {
            return ReactiveSecurityContextHolder.getContext().map { context ->
                context.authentication.principal as SecurityPrincipal
            }.awaitFirstOrNull()
        }
    }
}