package com.ovnny.notifications.exception

class NotificationNotFoundException(
    override val message: String = "O id da notificação informada está incorreta ou não existe"
) : IllegalArgumentException(message)