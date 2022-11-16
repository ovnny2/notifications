package com.ovnny.notifications.model.notification

import java.time.LocalDateTime

class NotificationResponse(
    val id: String,
    val title: String,
    val description: String,
    val html: String?,
    val author: String? = null,
    val pinned: Boolean?,
    val active: Boolean?,
    val priority: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime
)