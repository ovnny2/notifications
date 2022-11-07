package com.ovnny.notifications.exception

class NotificationNotFoundException(
    msg: String? = "O id da notificação informada está incorreta ou não existe"
) : IllegalArgumentException(msg)