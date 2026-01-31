package org.shakirgamzaev.threadsclonebackend.service

import org.shakirgamzaev.threadsclonebackend.model.SearchedUser
import org.shakirgamzaev.threadsclonebackend.model.User
import org.shakirgamzaev.threadsclonebackend.model.UserClaims
import org.shakirgamzaev.threadsclonebackend.model.UserThread
import org.shakirgamzaev.threadsclonebackend.model.dto.UserDTO
import org.shakirgamzaev.threadsclonebackend.repository.ThreadsRepository
import org.shakirgamzaev.threadsclonebackend.repository.UserRepo
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Service

/*
Utility class for fetching data from postresql database. This class simply calls methods on classes annotated with
@Repository, and fetches whatever data is required.
 */
@Service
class DataBaseOps(
    private val userRepo: UserRepo,
    private val userThreadsRepo: ThreadsRepository
) {

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


    fun fetchAllThreadsForUser(userClaims: UserClaims): List<UserThread> {
        val threads = userThreadsRepo.fetchThreads(userClaims)
        return threads
    }

    fun getAllSearchedUsers(): List<SearchedUser> {
        return userRepo.getAllUsers()
    }

    fun getSearchedUsersByFilter(filter: String): List<SearchedUser> {
        return userRepo.getUsersByFilter(filter = filter)
    }
}