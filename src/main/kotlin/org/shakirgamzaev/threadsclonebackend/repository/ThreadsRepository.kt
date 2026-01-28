package org.shakirgamzaev.threadsclonebackend.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository


// repo class that works with a table of threads. Apis will use this class to fetch threads.
@Repository
class ThreadsRepository(val jdbcTemplate: JdbcTemplate) {
    val threadsMapper = RowMapper{rs, _ ->
        Thread()
    }

    fun fetchThreads() {

    }
}