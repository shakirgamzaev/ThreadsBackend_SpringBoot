package org.shakirgamzaev.threadsclonebackend.controller

import org.shakirgamzaev.threadsclonebackend.model.UserThread
import org.shakirgamzaev.threadsclonebackend.model.UserClaims
import org.shakirgamzaev.threadsclonebackend.service.DataBaseOps
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


/**
 * Controller responsible for managing thread-related API endpoints.
 *
 * @constructor Initializes the controller with the required database service.
 *
 * @param dataBaseService A service handling database operations for threads and users.
 */
@RestController
@RequestMapping("/api/threads")
class ThreadsController(
    private val dataBaseService: DataBaseOps
) {

    @GetMapping("")
    fun getThreads(@AuthenticationPrincipal user: UserClaims): ResponseEntity<List<UserThread>> {
        println("user trying to access threads: ${user.userName}")
        val threads = dataBaseService.fetchAllThreadsForUser(userClaims = user)

        return ResponseEntity.ok().body(threads)
    }

    //client calls this api endpoint to create and post a new thread
    @PostMapping
    fun postNewThread(@AuthenticationPrincipal userClaims: UserClaims, @RequestBody userThread: UserThread): ResponseEntity<Unit> {
        println("user ${userClaims.userName} posting new thread!")
        dataBaseService.postNewThread(userThread)
        return ResponseEntity.ok().build()
    }
}