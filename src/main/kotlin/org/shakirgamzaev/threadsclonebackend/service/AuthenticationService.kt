package org.shakirgamzaev.threadsclonebackend.service

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationService {
    fun checkIfPasswordCorrect(userPassword: String, hashedPassword: String): Boolean {
        val passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
        return passwordEncoder.matches(userPassword, hashedPassword)
    }
}