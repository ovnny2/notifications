package com.ovnny.notifications.model.notifications

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Id

@Document(value = "notifications")
data class Notification(
    @get:Id
    val id: String? = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val html: String?,
    val author: String,
    val pinned: Boolean,
    val active: Boolean,
    val priority: String,
    val groups: List<Groups>,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

data class Groups(
    val id: String,
    val name: String
)