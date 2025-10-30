package com.pinta.homeserver.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.pinta.homeserver.repository.AuthoritiesRepository
import com.pinta.homeserver.repository.UserRepository
import com.pinta.homeserver.security.SecurityPrincipal
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val authsRepository: AuthoritiesRepository,
    @param:Value("\${pinta.home.jwt.key}") private val jwtKey: String,
    @param:Value("\${pinta.home.jwt.expiration}") private val jwtExpiration: Long,
) {

    companion object {
        const val JWT_ISSUER = "pinta"
    }

    fun login(email: String, password: String): String {
        val user = userRepository.findByEmailAndPassword(email, password) ?: throw ResponseStatusException(
            HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value())
        )

        val auths = authsRepository.findByUserId(user.id)

        return generateJWTToken(
            SecurityPrincipal(
                user.id,
                user.name,
                user.email,
                auths.map { SimpleGrantedAuthority(it.name) }
            )
        )
    }

    fun convertHeaderToPrincipal(authHeader: String?): SecurityPrincipal? {
        if (authHeader == null) {
            return null
        }

        return if (authHeader.startsWith("Bearer")) {
            convertJWTTokenToPrincipal(
                authHeader.substringAfter("Bearer").trim()
            )
        } else null
    }

    private fun convertJWTTokenToPrincipal(token: String): SecurityPrincipal? {
        try {
            val decodedJWT: DecodedJWT = JWT.require(getAlgorithm())
                .withIssuer(JWT_ISSUER)
                .build()
                .verify(token)

            return SecurityPrincipal(
                decodedJWT.getClaim("id").asLong(),
                decodedJWT.getClaim("username").asString(),
                decodedJWT.getClaim("email").asString(),
                decodedJWT.getClaim("auths").asString()
                    .split(",")
                    .filter { StringUtils.hasText(it) }
                    .map { SimpleGrantedAuthority(it) }
            )
        } catch (_: Exception) {
            return null
        }

    }

    private fun generateJWTToken(principal: SecurityPrincipal): String {
        val algorithm: Algorithm = getAlgorithm()
        return JWT.create()
            .withExpiresAt(Instant.now().plusMillis(jwtExpiration))
            .withIssuer(JWT_ISSUER)
            .withClaim("id", principal.id)
            .withClaim("username", principal.username)
            .withClaim("email", principal.email)
            .withClaim("auths", principal.authorities.joinToString(",") { it.authority })
            .sign(algorithm)
    }

    private fun getAlgorithm(): Algorithm {
        return Algorithm.HMAC256(jwtKey)
    }

}