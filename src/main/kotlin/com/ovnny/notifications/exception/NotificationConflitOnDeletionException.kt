package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class NotificationConflitOnDeletionException(message: String?, status: HttpStatus) : IllegalStateException(message)