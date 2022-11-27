package com.ovnny.notifications.service

import com.ovnny.notifications.exception.NotificationConflitOnDeletionException
import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.exception.msg.NotificationMessages
import com.ovnny.notifications.model.notification.Notification
import com.ovnny.notifications.model.notification.NotificationInfo
import com.ovnny.notifications.model.notification.NotificationRequest
import com.ovnny.notifications.model.notification.NotificationResponse
import com.ovnny.notifications.repository.NotificationRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class NotificationService(
    val repository: NotificationRepository,
) {

    @Transactional
    fun createNotification(request: NotificationRequest): NotificationResponse {
        val notification = toModel(request)
        repository.save(notification)

        return toResponse(notification)
    }

    fun getNotificationById(id: String): Notification {
        return repository.findById(id).orElseThrow {
            NotificationNotFoundException(NotificationMessages.NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND)
        }
    }

    fun getAllNotifications(): List<NotificationResponse> {
        val notificationsList = repository.findAll() as List<Notification>
        return notificationsList.map { toResponse(it) }
    }

    @Transactional
    fun notificationStateToggle(id: String): String {

        val notification = repository.findById(id).orElseThrow {
            NotificationNotFoundException(NotificationMessages.NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND)
        }

        val updatedNotificationStatus = notification.status.copy(
            active = !notification.status.active
        )

        val newNotification = repository.save(
            notification.copy(
                status = updatedNotificationStatus
            )
        )

        return when (newNotification.status.active) {
            true -> "A notificação foi ativada com sucesso"
            else -> "A notificação foi desativada com sucesso"
        }
    }

    @Transactional
    fun updateExistingNotification(id: String, updateRequest: NotificationRequest): NotificationResponse? {

        val original = repository.findById(id).orElseThrow {
            NotificationNotFoundException(NotificationMessages.NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND)
        }

        val updates = Notification(
            title = updateRequest.title,
            description = updateRequest.description,
            html = updateRequest.html,
            status = NotificationInfo(
                author = updateRequest.author,
                pinned = updateRequest.pinned!!,
                active = updateRequest.active,
                priority = updateRequest.priority,
                groups = updateRequest.groups,
                parentId = id
            )
        )

        repository.save(original)
        repository.save(updates)

        return toResponse(updates)
    }

    @Transactional
    fun deleteNotification(id: String) {
        val notification = repository.findById(id).orElseThrow {
            NotificationNotFoundException(NotificationMessages.NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND)
        }

        takeIf {
            notification.status.parentId?.isEmpty() ?: throw NotificationConflitOnDeletionException(
                NotificationMessages.RULE_BUSINESS_BROKEN_MESSAGE.msg,
                HttpStatus.METHOD_NOT_ALLOWED
            )

        }.apply { repository.delete(notification) }
    }

    fun toModel(request: NotificationRequest): Notification {
        return Notification(
            title = request.title,
            description = request.description,
            html = request.html,
            status = NotificationInfo(
                parentId = null,
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
            id = notification.id,
            title = notification.title,
            description = notification.description,
            html = notification.html,
            author = notification.status.author,
            pinned = notification.status.pinned,
            active = notification.status.active,
            priority = notification.status.priority,
            createdAt = notification.createdAt!!,
            lastUpdate = notification.updatedAt!!,
            parentId = notification.status.parentId
        )
    }
}