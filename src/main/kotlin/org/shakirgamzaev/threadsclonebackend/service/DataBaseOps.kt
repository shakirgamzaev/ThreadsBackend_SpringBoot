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
Utility class that handles all calls into Repository classes. If an api needs to talk to a database, whether to fetch
 rows, or insert rows, this is the class through which they go through.
 */
@Service
class DataBaseOps(
    private val userRepo: UserRepo,
    private val threadsRepo: ThreadsRepository
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
        val threads = threadsRepo.fetchThreads(userClaims)
        return threads
    }

    fun getAllSearchedUsers(userId: Long): List<SearchedUser> {
        return userRepo.getAllUsers(userId = userId)
    }

    fun getSearchedUsersByFilter(filter: String, userId: Long): List<SearchedUser> {
        return userRepo.getUsersByFilter(filter = filter, userId = userId)
    }


    fun followAnotherUser(
        currentUserId: Long,
        idOfUserToFollow: Long,
        shouldFollow: Boolean
    ) {
        userRepo.followAnotherUser(
            currentUserId = currentUserId,
            idOfUserToFollow = idOfUserToFollow,
            shouldFollow = shouldFollow
        )
    }



    fun postNewThread(userThread: UserThread) {
        threadsRepo.insertNewThread(userThread)
    }

    fun updateUserProfile(
        userId: Long,
        fullName: String,
        bio: String
    ) {
        userRepo.updateUserProfile(
            userId = userId,
            fullName = fullName,
            bio = bio
        )
    }

}