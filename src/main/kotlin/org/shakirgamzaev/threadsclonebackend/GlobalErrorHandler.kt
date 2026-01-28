package org.shakirgamzaev.threadsclonebackend

import org.shakirgamzaev.threadsclonebackend.model.Error.ApiError
import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalErrorHandler {

    @ExceptionHandler(DuplicateKeyException::class)
    fun handleDuplicateKey(error: DuplicateKeyException): ResponseEntity<ApiError> {
       return ResponseEntity.status(409).body(ApiError(409, "a user with this email already exists"))
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun userNotFoundError(error: DataAccessException): ResponseEntity<ApiError> {
        print("no user email error caught")
        val error = ApiError(404,  "user with this email not found")

        return ResponseEntity.status(404).body(error)
    }


    @ExceptionHandler(Exception::class)
    fun handleGeneralError(error: Exception): ResponseEntity<ApiError> {
        // In a real app, use a logger here: logger.error("Unhandled exception", error)
        println("Unhandled exception: ${error.message}")

        return ResponseEntity.status(500).body(ApiError(500, "Something went wrong on our end"))
    }

}