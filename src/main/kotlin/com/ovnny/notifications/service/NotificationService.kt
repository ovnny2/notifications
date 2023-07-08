package com.ovnny.notifications.service

import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.model.notification.Notification
import com.ovnny.notifications.model.notification.NotificationInfo
import com.ovnny.notifications.model.notification.NotificationRequest
import com.ovnny.notifications.model.notification.NotificationResponse
import com.ovnny.notifications.repository.NotificationRepository
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NotificationService(
    private val repository: NotificationRepository,
    private val messageSource: MessageSource
) {

    @Transactional
    fun createNotification(notification: Notification): NotificationResponse {
        repository.save(notification)

        return toResponse(notification)
    }

    fun getNotificationById(id: String): Notification {
        return repository.findById(id)
            .orElseThrow { NotificationNotFoundException(HttpStatus.NOT_FOUND) }
    }

    fun getAllNotifications(): List<NotificationResponse?> {
        val notifications = repository.findAll()

        return notifications.map { toResponse(it) }
    }

    @Transactional
    fun toggleNotificationState(id: String): String {
        val notification = repository.findById(id)
            .orElseThrow { NotificationNotFoundException(HttpStatus.NOT_FOUND) }

        notification.status.active = !notification.status.active

        repository.save(notification)

        return when (notification.status.active) {
            true -> messageSource.getMessage("ActiveNotification",
                arrayOf(notification.title), Locale("pt"))

            else -> messageSource.getMessage("InactiveNotification",
                arrayOf(notification.title), Locale("pt"))
        }
    }

    @Transactional
    fun updateNotification(id: String, updateRequest: NotificationRequest): NotificationResponse? {

        val originalNotification = repository.findById(id)
            .orElseThrow { NotificationNotFoundException(HttpStatus.NOT_FOUND) }

        val updates = originalNotification.copy(
            title = updateRequest.title,
            description = updateRequest.description,
            html = updateRequest.html,
            status = NotificationInfo(
                author = updateRequest.author,
                pinned = updateRequest.pinned ?: originalNotification.status.pinned,
                active = updateRequest.active,
                priority = updateRequest.priority,
                groups = updateRequest.groups
            )
        )

        originalNotification.status.active = false

        repository.save(originalNotification)
        repository.save(updates)

        return toResponse(updates)
    }

    @Transactional
    fun deleteNotification(id: String) {
        val notification = repository.findById(id)
            .orElseThrow { NotificationNotFoundException(HttpStatus.NOT_FOUND) }

        repository.delete(notification)
    }

    fun toModel(request: NotificationRequest): Notification {
        return Notification(
            title = request.title,
            description = request.description,
            html = request.html,
            status = NotificationInfo(
                priority = request.priority,
                active = request.active,
                pinned = request.pinned ?: false,
                author = request.author,
                groups = request.groups
            )
        )
    }

    fun toResponse(notification: Notification): NotificationResponse {
        return NotificationResponse(
            id = notification.id!!,
            title = notification.title,
            description = notification.description,
            html = notification.html,
            author = notification.status.author,
            pinned = notification.status.pinned,
            active = notification.status.active,
            priority = notification.status.priority,
            createdAt = notification.createdAt!!,
            lastUpdate = notification.updatedAt!!
        )
    }
}