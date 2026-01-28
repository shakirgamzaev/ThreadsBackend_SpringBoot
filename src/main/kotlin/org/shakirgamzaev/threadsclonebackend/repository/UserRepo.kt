package org.shakirgamzaev.threadsclonebackend.repository

import org.shakirgamzaev.threadsclonebackend.model.User
import org.shakirgamzaev.threadsclonebackend.model.dto.UserDTO
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class UserRepo(val jdbcTemplate: JdbcTemplate) {

    // Maps database row to user model
    private val userMapper = RowMapper<User> { rs, num ->
        User(
            id = rs.getLong("user_id"),
            userName = rs.getString("user_name"),
            email = rs.getString("email"),
            passwordHash = rs.getString("password_hash"),
            imageUrl = rs.getString("image_url"),
            fullName = rs.getString("full_name")
        )
    }

    private val userDTOMapper = RowMapper<UserDTO> {rs, _ ->
        UserDTO(
            id = rs.getLong("user_id"),
            userName = rs.getString("user_name"),
            email = rs.getString("email"),
            imageURL = rs.getString("image_url"),
            fullName = rs.getString("full_name")
        )
    }

    //this function returns the userDTO, which contains all of user info, except the password hash, which the user
    // does not need.
    fun returnUserDTO(email: String): UserDTO {
        val sql = "select user_id, user_name, email, image_url, full_name from dev_schema.users where email = ?"

        val user = jdbcTemplate.queryForObject(sql, userDTOMapper, email)

        return user
    }



    fun findUserByEmail(email: String): User? {
        val sql = "select * from dev_schema.users where email ilike ?"

        val users = jdbcTemplate.query(sql, userMapper, email)
        return users.singleOrNull()
    }

    fun doesUserExist(userName: String): Boolean {
        val sql = "select user_name from dev_schema.users where user_name = ?"
        val result = jdbcTemplate.query(sql, {rs, _ -> rs.getString("user_name")}, userName)

        return result.isNotEmpty()
    }

    fun createNewUser(
        userName: String,
        fullName: String,
        password: String, email: String): UserDTO {
        val sql = """insert into dev_schema.users 
    (user_name, email, 
     password_hash, image_url, full_name) values (?, ?, ?, ?, ?) returning user_id, user_name, email, image_url, 
     full_name"""

        val result = jdbcTemplate.queryForObject(sql, userDTOMapper,
            userName,
            email,
            password,
            null,
            fullName
            )
        return result
    }
}