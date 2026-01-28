package org.shakirgamzaev.threadsclonebackend.controller

import org.shakirgamzaev.threadsclonebackend.model.UserThread
import org.shakirgamzaev.threadsclonebackend.model.UserClaims
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/threads")
class ThreadsController {

    @GetMapping("")
    fun getThreads(@AuthenticationPrincipal user: UserClaims): ResponseEntity<List<UserThread>> {
        println("user trying to access threads: ${user.userName}")


        return ResponseEntity.ok().body(listOf())
    }
}