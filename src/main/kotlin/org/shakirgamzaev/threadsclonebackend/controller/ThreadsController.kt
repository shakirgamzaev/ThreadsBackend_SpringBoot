package org.shakirgamzaev.threadsclonebackend.controller

import org.shakirgamzaev.threadsclonebackend.model.UserThread
import org.shakirgamzaev.threadsclonebackend.model.UserClaims
import org.shakirgamzaev.threadsclonebackend.service.DataBaseOps
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


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
}