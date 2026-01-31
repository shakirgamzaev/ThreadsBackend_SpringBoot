package org.shakirgamzaev.threadsclonebackend.controller

import org.shakirgamzaev.threadsclonebackend.model.SearchedUser
import org.shakirgamzaev.threadsclonebackend.service.DataBaseOps
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/allUsers")
class UsersController(
    val dataBaseOps: DataBaseOps
) {

    @GetMapping
    fun getUsers(@RequestParam(required = false) userName: String?): ResponseEntity<*> {
        var listOfUsers = mutableListOf<SearchedUser>()
        if (userName == null) {
            listOfUsers = dataBaseOps.getAllSearchedUsers() as MutableList<SearchedUser>
        }
        else {
            listOfUsers = dataBaseOps.getSearchedUsersByFilter(filter = userName) as MutableList<SearchedUser>
        }
        return ResponseEntity.ok().body(listOfUsers)
    }
}