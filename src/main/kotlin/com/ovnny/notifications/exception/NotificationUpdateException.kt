package com.ovnny.notifications.exception

class NotificationUpdateException(
    override val message: String, cause: Throwable
): IllegalStateException(message, cause)