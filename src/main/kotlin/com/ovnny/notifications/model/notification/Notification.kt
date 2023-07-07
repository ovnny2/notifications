package com.ovnny.notifications.model.notification

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(value = "notifications")
data class Notification(

    @Id
    val id: String? = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val html: String?,
    val status: NotificationInfo,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),

)

data class NotificationInfo(
    val priority: String,
    var active: Boolean,
    var pinned: Boolean,
    val author: String,
    val groups: List<Groups>
)

data class Groups(
    val id: String,
    val name: String
)