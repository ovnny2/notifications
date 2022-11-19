package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class NotificationConflitOnDeletionException(override val message: String?, val status: HttpStatus) : IllegalStateException(message)