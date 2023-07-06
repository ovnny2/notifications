package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class NotificationUpdateException(val status: HttpStatus): IllegalStateException() {
    override lateinit var message: String
}