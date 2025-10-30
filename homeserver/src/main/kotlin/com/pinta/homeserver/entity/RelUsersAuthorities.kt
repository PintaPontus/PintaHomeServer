package com.pinta.homeserver.entity

import jakarta.persistence.*

@Entity
@Table(name = "rel_users_authorities")
data class RelUsersAuthorities(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val userId: Long,
    val authorityId: Long,
)