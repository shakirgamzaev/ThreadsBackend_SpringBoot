package org.shakirgamzaev.threadsclonebackend.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.shakirgamzaev.threadsclonebackend.model.dto.UserDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date
import java.util.HexFormat
import javax.crypto.spec.SecretKeySpec

@Service
class JWTService {
    @Value("\${jwt.secretKey}")
    private lateinit var secretKeyStr: String

    /**
     * Creates signed JWT token with user claims
     */
    fun createAndSignJWTToken(user: UserDTO): String {
        val keyBytes = HexFormat.of().parseHex(secretKeyStr)

        val key = SecretKeySpec(keyBytes, "HmacSHA256")

        val now = Date()
        val validityOfJWTInMS: Long = 1000 * 60 * 60 * 24 //the jwt token will have an expiry date 1 day from the
        // moment of creation



        val jwtBuilder = Jwts.builder()
        return jwtBuilder
            .subject(user.userName)
            .claim("id", user.id)
            .claim("email", user.email)
            .claim("fullName", user.fullName)
            .issuedAt(now)
            .expiration(Date(now.time + validityOfJWTInMS))
            .signWith(key, Jwts.SIG.HS256)
            .compact()

    }



    /**
     * Validates token and returns claims payload
     */
    fun validateToken(token: String): Claims {
        val keyBytes = HexFormat.of().parseHex(secretKeyStr)
        val key = SecretKeySpec(keyBytes, "HmacSHA256")

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

}