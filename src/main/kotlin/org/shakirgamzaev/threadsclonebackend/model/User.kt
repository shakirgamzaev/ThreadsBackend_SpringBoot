package org.shakirgamzaev.threadsclonebackend.model

//data class that is the direct mapping to SQL database table, and this is one of the main tables of my project: it
// holds a list of all registered users of my backend
data class User (
    val id: Long,
    val userName: String,
    val email: String,
    val passwordHash: String,
    val fullName: String,
    val imageUrl: String?,
    val bio: String
)