package com.pinta.homeserver.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val password: String,
    val email: String,
    val image: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)