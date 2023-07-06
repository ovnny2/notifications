package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class NotificationNotFoundException(val httpStatus: HttpStatus) : RuntimeException() {
    override lateinit var message: String
}