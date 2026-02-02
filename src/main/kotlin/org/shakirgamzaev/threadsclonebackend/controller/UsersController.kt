package org.shakirgamzaev.threadsclonebackend.controller

import org.shakirgamzaev.threadsclonebackend.model.SearchedUser
import org.shakirgamzaev.threadsclonebackend.model.UserClaims
import org.shakirgamzaev.threadsclonebackend.service.DataBaseOps
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/allUsers")
class UsersController(
    val dataBaseOps: DataBaseOps
) {

    /**
     * Returns users, optionally filtered, for authenticated user
     */
    @GetMapping
    fun getUsers(
        @RequestParam(required = false) userName: String?,
        @AuthenticationPrincipal userClaims: UserClaims
    ): ResponseEntity<*> {
        println("getUsers api reached!")

        var listOfUsers = mutableListOf<SearchedUser>()
        if (userName == null) {
            listOfUsers = dataBaseOps.getAllSearchedUsers(userId = userClaims.id) as MutableList<SearchedUser>
        }
        else {
            listOfUsers = dataBaseOps.getSearchedUsersByFilter(filter = userName, userId = userClaims.id) as
                    MutableList<SearchedUser>
        }
        return ResponseEntity.ok().body(listOfUsers)
    }


    //follows or unfollows the tagetUser, depending on whether shouldFollow  is set to true
    @PostMapping("/follow/{id}/{shouldFollow}")
    fun followUser(
        @AuthenticationPrincipal userClaims: UserClaims, @PathVariable id: Long,
        @PathVariable shouldFollow: Boolean
    ): ResponseEntity<Unit> {
        println("allUsers/follow api is called")

        // userClaims.id is the person DOING the following
        // id (from path) is the person BEING followed
        dataBaseOps.followAnotherUser(currentUserId = userClaims.id, idOfUserToFollow = id, shouldFollow = shouldFollow)
        return ResponseEntity.ok().build()

    }


    @PostMapping("/updateProfile")
    fun updateUserProfile(
        @AuthenticationPrincipal userClaims: UserClaims,
        @RequestBody bio: String
    ): ResponseEntity<Unit> {
        println("allUsers/updateProfile api endpoint reached")
        val userId = userClaims.id
        dataBaseOps.updateUserProfile(userId = userId, fullName = userClaims.fullName, bio = bio)

        return ResponseEntity.ok().build()
    }


}