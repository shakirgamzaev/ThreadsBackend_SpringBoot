package org.shakirgamzaev.threadsclonebackend.repository

import org.shakirgamzaev.threadsclonebackend.model.UserClaims
import org.shakirgamzaev.threadsclonebackend.model.UserThread
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository


// repo class that works with a table of threads. Apis will use this class to fetch threads.
@Repository
class ThreadsRepository(val jdbcTemplate: JdbcTemplate) {

    // Maps database rows to thread objects
    private val threadsMapper = RowMapper{rs, _ ->
        UserThread(
            imageURL = rs.getString("image_url"),
            userName = rs.getString("user_name"),
            content = rs.getString("content"),
            postDate = rs.getTimestamp("dateposted"),
            userId = rs.getLong("userid"),
            id = rs.getLong("id")
        )
    }



    fun fetchThreads(userClaims: UserClaims): List<UserThread> {
        val sql = """ select
                    threads.content,
                    threads.dateposted,
                    threads.userid,
                    threads.id,
                    u.user_name,
                    u.image_url
                 from dev_schema.threads
                inner join dev_schema.follows f on f.following_id = threads.userid
                inner join dev_schema.users u on threads.userid = u.user_id
                where f.follower_id = ?
                order by threads.dateposted desc"""

        var threads = jdbcTemplate.query(sql, threadsMapper, userClaims.id)
        return threads
    }


    fun insertNewThread(userThread: UserThread) {
        val sql = """insert into dev_schema.threads (dateposted, content, userid)
            values (?, ?, ?)
            """
        val result = jdbcTemplate.update(sql, userThread.postDate, userThread.content, userThread.userId)
    }
}