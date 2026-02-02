package org.shakirgamzaev.threadsclonebackend.model

//class that contains the claims from JWT token
data class UserClaims(
    val id: Long,
    val userName: String,
    val email: String,
    val fullName: String
)