package com.pinta.homeserver.security

import com.pinta.homeserver.service.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authService: AuthService
) {

    @Bean
    fun httpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.csrf { csrf -> csrf.disable() }
            .httpBasic { basic -> basic.disable() }
            .formLogin { form -> form.disable() }
            .authorizeExchange { exchanges ->
                exchanges!!.pathMatchers("/auth/login").permitAll()
                    .anyExchange().authenticated()
            }.addFilterBefore(
                { exchange, chain ->
                    val authPrincipal = authService.convertHeaderToPrincipal(
                        exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
                    )
                    if (authPrincipal != null) {
                        val authentication = OneTimeTokenAuthenticationToken(
                            authPrincipal,
                            authPrincipal.authorities
                        )

                        chain.filter(exchange).contextWrite(
                            ReactiveSecurityContextHolder.withAuthentication(authentication)
                        )
                    } else chain.filter(exchange)
                },
                SecurityWebFiltersOrder.AUTHENTICATION
            )
        return http.build()
    }

}