package com.pinta.homeserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [
        UserDetailsServiceAutoConfiguration::class,
        ReactiveUserDetailsServiceAutoConfiguration::class
    ]
)
class HomeServerApplication

fun main(args: Array<String>) {
    runApplication<HomeServerApplication>(*args)
}
