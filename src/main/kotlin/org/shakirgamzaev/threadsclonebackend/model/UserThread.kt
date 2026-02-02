package org.shakirgamzaev.threadsclonebackend.model

import java.util.Date


/*
    private var imageURL: String? = nil
    var image: Data? = nil
    var userName: String
    var content: String
    var postDate: Date
    var userId: Int64
 */
data class UserThread (
    val imageURL: String?, //image url is retrieved by a join from users table
    val userName: String,
    val content: String,
    var postDate: Date,
    var userId: Long,
    var id: Long? //the actual id of a thread
)