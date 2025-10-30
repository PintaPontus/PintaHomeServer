package com.pinta.homeserver.entity

import jakarta.persistence.*

@Entity
@Table(name = "authorities")
data class Authority(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
)