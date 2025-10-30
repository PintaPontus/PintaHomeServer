package com.pinta.homeserver.repository

import com.pinta.homeserver.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AuthoritiesRepository : JpaRepository<Authority, Long> {

    @Query(
        """
        SELECT a 
        FROM Authority a
        JOIN RelUsersAuthorities rua ON rua.authorityId = a.id
        JOIN User u ON u.id = rua.userId
        WHERE u.id = :userId
    """
    )
    fun findByUserId(
        @Param("userId") userId: Long
    ): List<Authority>
}