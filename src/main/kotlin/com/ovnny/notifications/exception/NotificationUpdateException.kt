package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class NotificationUpdateException(
    override val message: String, val status: HttpStatus
): IllegalStateException(message)