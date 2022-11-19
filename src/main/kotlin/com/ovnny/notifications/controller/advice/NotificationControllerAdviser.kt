package com.ovnny.notifications.controller.advice

import com.ovnny.notifications.exception.MessageError
import com.ovnny.notifications.exception.NotificationConflitOnDeletionException
import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.exception.msg.NotificationMessages
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
            MessageError(NotificationMessages.GENERIC_MESSAGE.msg, HttpStatus.INTERNAL_SERVER_ERROR)
        )
    }

    @ExceptionHandler(NotificationNotFoundException::class)
    fun handle(ex: NotificationNotFoundException): ResponseEntity<MessageError> {
        val response = MessageError(NotificationMessages.NOT_FOUND_MESSAGE.msg, ex.status)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handle(ex: NotificationConflitOnDeletionException): ResponseEntity<MessageError> {
        return ResponseEntity.unprocessableEntity()
            .body(MessageError(NotificationMessages.RULE_BUSINESS_BROKEN_MESSAGE.msg, ex.status))
    }
}