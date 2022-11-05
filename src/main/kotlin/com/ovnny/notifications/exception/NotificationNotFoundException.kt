package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class NotificationNotFoundException(
    override val message: String = "O id da notificação informada está incorreta ou não existe",
    val status: HttpStatus = HttpStatus.NOT_FOUND
) : IllegalArgumentException(message)