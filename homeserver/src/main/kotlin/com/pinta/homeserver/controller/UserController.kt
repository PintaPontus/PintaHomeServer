package com.pinta.homeserver.controller

import com.pinta.homeserver.entity.User
import com.pinta.homeserver.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional


@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserRepository
){

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): Optional<User?> {
        return userRepository.findById(id)
    }
}