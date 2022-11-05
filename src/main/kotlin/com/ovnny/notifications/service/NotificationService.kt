package com.ovnny.notifications.service

import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.exception.UpdateNotificationException
import com.ovnny.notifications.model.notifications.Notification
import com.ovnny.notifications.model.notifications.NotificationRequest
import com.ovnny.notifications.model.notifications.NotificationResponse
import com.ovnny.notifications.repository.NotificationRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import javax.validation.Valid

@Service
class NotificationService(
    val repository: NotificationRepository
) {

    fun getNotification(id: String): Notification {
        return repository.findById(id).orElseThrow { NotificationNotFoundException() }
    }

    fun getAllNotifications(): List<NotificationResponse?> {
        val notifications = repository.findAll().toList()
        return notifications.map { toResponse(it) }
    }

    @Transactional
    fun saveNotification(notification: Notification) {
        repository.save(notification)
    }

    @Transactional
    fun updateExistingNotification(notificationAlterations: Notification): NotificationResponse? {
        val originalNotification = repository.findById(notificationAlterations.id)
            .orElseThrow { NotificationNotFoundException() }

        if (originalNotification.equals(notificationAlterations)) throw UpdateNotificationException()

        val alteredNotification = Notification(
            notificationAlterations.title,
            notificationAlterations.description,
            notificationAlterations.html,
            originalNotification.author,
            notificationAlterations.groups,
            id = originalNotification.id
        )

        return toResponse(alteredNotification)
    }

    fun toModel(@Valid request: NotificationRequest)= Notification(
        request.title, request.description, request.html, request.author, request.groups
    )

    fun toResponse(notification: Notification) = NotificationResponse(
            notification.id, notification.title, notification.description,
            notification.html, notification.createdAt, notification.updatedAt
    )
}