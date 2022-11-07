package com.ovnny.notifications.exception

import java.lang.IllegalArgumentException

class NotificationConflitOnDeletionException(
    msg: String? = "O status da notificação está ativa e portanto não pode ser deletada"
) : IllegalArgumentException(msg)