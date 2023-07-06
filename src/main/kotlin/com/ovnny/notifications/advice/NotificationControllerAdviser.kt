package com.ovnny.notifications.advice

import com.ovnny.notifications.exception.MessageError
import com.ovnny.notifications.exception.NotificationNotFoundException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*


@Component
@ControllerAdvice
class NotificationControllerAdviser(
    private val messageSource: MessageSource
) {

    @ExceptionHandler(RuntimeException::class)
    fun handle(ex: Exception): ResponseEntity<MessageError> {
        val messageError = MessageError(resolveMessage(ex), HttpStatus.INTERNAL_SERVER_ERROR)

        return ResponseEntity.status(messageError.status).body(messageError)
    }

    @ExceptionHandler(NotificationNotFoundException::class)
    fun handle(ex: NotificationNotFoundException): ResponseEntity<MessageError> {
        val messageError = MessageError(resolveMessage(ex), ex.httpStatus)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError)
    }

    fun resolveMessage(ex: Exception): String {

        val locale = Locale("pt")

        return messageSource.getMessage("${ex::class.simpleName}", null, locale)
    }
}