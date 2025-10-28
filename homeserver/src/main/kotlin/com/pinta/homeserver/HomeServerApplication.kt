package com.pinta.homeserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HomeServerApplication

fun main(args: Array<String>) {
    runApplication<HomeServerApplication>(*args)
}
