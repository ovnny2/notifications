package com.ovnny.notifications.service

import com.ovnny.notifications.model.notifications.Notification
import com.ovnny.notifications.model.notifications.NotificationRequest
import com.ovnny.notifications.model.notifications.NotificationResponse
import org.springframework.stereotype.Service

@Service
class NotificationService {
    fun toModel(request: NotificationRequest): Notification {
        return Notification(
            title = request.title,
            description = request.description,
            html = request.html,
            author = request.author,
            groups = request.groups
        )
    }

    fun toResponse(notification: Notification): NotificationResponse {
        return NotificationResponse(
            notification.id,
            notification.title,
            notification.description,
            notification.html,
            notification.createdAt,
            notification.updatedeAt
        )
    }
}
