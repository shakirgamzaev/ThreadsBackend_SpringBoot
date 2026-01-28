package org.shakirgamzaev.threadsclonebackend.service

import org.shakirgamzaev.threadsclonebackend.model.User
import org.shakirgamzaev.threadsclonebackend.model.dto.UserDTO
import org.shakirgamzaev.threadsclonebackend.repository.UserRepo
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Service

@Service
class DataBaseOps(val userRepo: UserRepo) {

    fun getUserDTO(email: String): UserDTO {
        return userRepo.returnUserDTO(email)
    }

    fun findUserByEmail(email: String): User? {
        val user = userRepo.findUserByEmail(email)
        return user
    }



    fun doesUserExist(userName: String): Boolean {
        return userRepo.doesUserExist(userName)
    }

    fun createNewUser(userName: String, fullName: String, password: String, email: String): UserDTO {
            val passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
            val hashedPassword = passwordEncoder.encode(password)
        return userRepo.createNewUser(userName, fullName, hashedPassword!!, email)
    }



}