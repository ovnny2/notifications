package com.ovnny.notifications.model.notifications

import java.time.LocalDateTime

class NotificationResponse(
    val id: String,
    val title: String,
    val description: String,
    val html: String?,
    val createdAt: LocalDateTime,
    val updatedeAt: LocalDateTime
)

