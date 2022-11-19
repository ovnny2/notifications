package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class NotificationNotFoundException(
    override val message: String, val status: HttpStatus) : RuntimeException(message)