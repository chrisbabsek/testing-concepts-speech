package de.babsek.demo.axontesting.remote

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun on(exception: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                errorType = exception::class.java.simpleName,
                errorMessage = exception.message
                )
            )
    }

    data class ErrorResponse(
        val errorType: String,
        val errorMessage: String?
    )
}