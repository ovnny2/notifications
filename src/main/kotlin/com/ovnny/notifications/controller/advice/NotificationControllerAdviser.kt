package com.ovnny.notifications.controller.advice

import com.ovnny.notifications.exception.MessageError
import com.ovnny.notifications.exception.NotificationConflitOnDeletionException
import com.ovnny.notifications.exception.NotificationNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@Component
@ControllerAdvice
class NotificationControllerAdviser {

    @ExceptionHandler(RuntimeException::class)
    fun handle(ex: Exception): ResponseEntity<MessageError> {
        return ResponseEntity.internalServerError().body(
            MessageError("Um erro inesperado ocorreu. Tente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR)
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handle(ex: NotificationNotFoundException): ResponseEntity<MessageError> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handle(ex: NotificationConflitOnDeletionException): ResponseEntity<MessageError> {
        return ResponseEntity.unprocessableEntity()
            .body(MessageError(ex.message.toString(), HttpStatus.UNPROCESSABLE_ENTITY))
    }
}