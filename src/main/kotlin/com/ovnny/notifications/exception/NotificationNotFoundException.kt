package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException.NotFound

class NotificationNotFoundException(
    override val message: String, status: HttpStatus) : NoSuchElementException(message)