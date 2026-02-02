package org.shakirgamzaev.threadsclonebackend.model

data class SearchedUser(
    val id: Long,
    val userName: String,
    val imageURL: String?,
    val fullName: String,
    val bio: String,
    val followersCount: Int,
    val isFollowed: Boolean
)
