package org.shakirgamzaev.threadsclonebackend.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.shakirgamzaev.threadsclonebackend.model.UserClaims
import org.shakirgamzaev.threadsclonebackend.service.JWTService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTFilter (private val jwtService: JWTService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        //if the http request coming from client does not contain an Authorization: Bearer then hand off to spring
        // security filter chain, which will stop the request with an error.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val token = authHeader.substring(7)

        // Validates token; responds with error on expiration
        try {
            val claims = jwtService.validateToken(token)
            val id = (claims["id"] as Number).toLong()
            val email = claims["email"] as String
            val fullName = claims["fullName"] as String

            // Sets authenticated user details in a security context
            if (SecurityContextHolder.getContext().authentication == null) {

                val userName = claims.subject
                val userClaims = UserClaims(id = id, userName = userName, email = email, fullName = fullName)

                val authToken = UsernamePasswordAuthenticationToken(userClaims, null, emptyList())

                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authToken
            }

            filterChain.doFilter(request, response)
        }
        catch (e: io.jsonwebtoken.JwtException) {
            println("JWTFILTEr: JwtException ")
            response.status = 401
            response.contentType = "application/json"
            response.writer.write("""{"status": 401, "message": "Token has expired"}""")
            return
        }

        catch (e: Exception) {
            // 2. Unexpected Filter Bugs (NullPointer, ClassCast)
            // It is safer to catch this here so the client gets a JSON response
            println("JWTFilter: general exception")
            response.status = 500
            response.contentType = "application/json"
            response.writer.write("""{"status": 500, "message": "Internal Authentication Error"}""")
            return
        }
    }
}