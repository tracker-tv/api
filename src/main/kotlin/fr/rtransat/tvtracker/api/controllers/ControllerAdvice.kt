package fr.rtransat.tvtracker.api.controllers

import fr.rtransat.tvtracker.domain.shared.exceptions.ResourceNotFoundException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@Serializable
data class ErrorResponse(
    @SerialName("status") val statusCode: Int,
    val message: String,
    val name: String? = null
)

@Serializable
data class ValidationErrorResponse(
    @SerialName("status") val statusCode: Int,
    val message: String,
    val violations: Map<String, List<String?>>
)

@Serializable
data class Violation(val fieldName: String, val message: String?)

@RestControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun onResourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ErrorResponse {
        return ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun onMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException
    ): ValidationErrorResponse {
        val errors = mutableListOf<Violation>()
        for (fieldError in ex.bindingResult.fieldErrors) {
            errors.add(
                Violation(fieldError.field, fieldError.defaultMessage)
            )
        }

        return ValidationErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation constraint error",
            errors.groupBy(Violation::fieldName) { it.message }
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun globalExceptionHandler(ex: Exception, request: WebRequest): ErrorResponse {
        return ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.message!!,
            ex.javaClass.name
        )
    }
}
