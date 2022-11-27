package com.ovnny.notifications.model.notification

import java.time.LocalDateTime

data class NotificationResponse(
    val id: String,
    val title: String,
    val description: String,
    val html: String?,
    val author: String?,
    val pinned: Boolean,
    val active: Boolean,
    val priority: String,
    val createdAt: LocalDateTime,
    val lastUpdate: LocalDateTime,
    val parentId: String?
)