package org.shakirgamzaev.threadsclonebackend.model.dto

data class UserDTO(
    val id: Long,
    val userName: String,
    val email: String,
    val imageURL: String?,
    val fullName: String,
    val bio: String
)