package org.shakirgamzaev.threadsclonebackend.controller

import jakarta.servlet.http.HttpServletRequest
import org.shakirgamzaev.threadsclonebackend.model.Error.ApiError
import org.shakirgamzaev.threadsclonebackend.model.Forms.EmailPasswordForm
import org.shakirgamzaev.threadsclonebackend.model.Forms.SignUpForm
import org.shakirgamzaev.threadsclonebackend.model.dto.UserDTO
import org.shakirgamzaev.threadsclonebackend.service.AuthenticationService
import org.shakirgamzaev.threadsclonebackend.service.DataBaseOps
import org.shakirgamzaev.threadsclonebackend.service.JWTService
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api/auth")
class AuthController(val db: DataBaseOps,
                     val jwtService: JWTService,
                     val authService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginInfo: EmailPasswordForm): ResponseEntity<*> {
        val user = db.findUserByEmail(loginInfo.email)
        if (user == null) {
            return ResponseEntity.status(401).body(ApiError(401, "user email or password is incorrect"))
        }
        val password = loginInfo.password
        val isPasswordCorrect = authService.checkIfPasswordCorrect(password, user.passwordHash)
        if (!isPasswordCorrect) {
            return ResponseEntity.status(401).body(ApiError(401, "user email or password is incorrect"))
        }

        val userDTO = UserDTO(id = user.id,
            userName = user.userName,
            email = user.email,
            imageURL = user.imageUrl,
            fullName = user.fullName)

        val jwtToken = jwtService.createAndSignJWTToken(userDTO)

        return ResponseEntity.status(200).body(mapOf("user" to userDTO, "token" to jwtToken))
    }

    /**
     * Registers user; returns error or new user
     */
    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpForm: SignUpForm): ResponseEntity<*> {
        val userExist = db.doesUserExist(signUpForm.userName)
        if (userExist) {
            return ResponseEntity.status(409).body(ApiError(status = 409, "this userName is already taken!, please " +
                    "choose a different one or login"))
        }
        //if the user does not yet exist, create one, then create a JWT token for session tracking, and finally send
        val newUser = db.createNewUser(
            signUpForm.userName,
            signUpForm.fullName,
            signUpForm.password,
            signUpForm.email)

        val jwtToken = jwtService.createAndSignJWTToken(newUser)

        return ResponseEntity.status(200).body(
            mapOf<String, Any>("user" to newUser, "token" to jwtToken)
        )
    }


    /**
     * Gets the token from a user, cheks that it is valid, and not expired, and returns the UserDTO object back to
     * the user.
     * RETURN userDTO as data to IOS client
     */
    @GetMapping("/validate")
    fun validateToken(request: HttpServletRequest): ResponseEntity<*> {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer"))  {
            return ResponseEntity.status(401).body(ApiError(401, "Missing token"))
        }
        val jwtToken = authHeader.substring(7)

        return try {
            val claims = jwtService.validateToken(jwtToken)
            val email = claims["email"] as String
            val userDTO = db.getUserDTO(email)
            ResponseEntity.ok(userDTO)
        }
        catch (e: Exception) {
            return ResponseEntity.status(401).body(ApiError(401, "Token expired or invalid"))
        }
    }

}