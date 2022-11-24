package com.ovnny.notifications.service

import com.ovnny.notifications.exception.NotificationConflitOnDeletionException
import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.exception.msg.NotificationMessages.*
import com.ovnny.notifications.model.notification.Notification
import com.ovnny.notifications.model.notification.NotificationRequest
import com.ovnny.notifications.model.notification.NotificationResponse
import com.ovnny.notifications.repository.NotificationRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class NotificationService(
    val repository: NotificationRepository,
) {

    @Transactional
    fun createNotification(notification: Notification) {
        repository.save(notification)
    }

    fun getNotification(id: String): Notification? {
        return repository.findById(id)
            .orElseThrow { NotificationNotFoundException(NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND) }
            .copy()
    }

    fun getAllNotifications(): List<NotificationResponse> {
        val notificationsList = repository.findAll() as List<Notification>
        return notificationsList.map { toResponse(it) }
    }

    @Transactional
    fun notificationStateToggle(id: String): String {

        val notification = repository.findById(id)
            .orElseThrow { NotificationNotFoundException(NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND) }

        val notificationNewState = notification.copy(active = !notification.active)
        repository.delete(notification)
        repository.save(notificationNewState)

        val response = object {
            val turnedNotificationState = "the notification's state has changed from: \\ " +
                    "active: ${notification.active} :: [ old ] to\\" +
                    "active: ${notificationNewState.active} :: [ new ]"
        }

        return response.turnedNotificationState
    }

    @Transactional
    fun updateExistingNotification(id: String, updated: NotificationRequest): NotificationResponse? {

        val original = repository.findById(id)
            .orElseThrow { NotificationNotFoundException(NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND) }

        val updates = original.copy(
            title = updated.title,
            description = updated.description,
            html = updated.html,
            author = original.author,
            pinned = updated.pinned,
            active = original.active,
            priority = updated.priority,
            groups = updated.groups,
            createdAt = original.createdAt
        )

        repository.delete(original)
        repository.save(updates)

        return toResponse(updates)
    }

    @Transactional
    fun deleteNotification(id: String): String? {
        val notification = repository.findById(id)
            .orElseThrow { NotificationNotFoundException(NOT_FOUND_MESSAGE.msg, HttpStatus.NOT_FOUND) }

        if (notification.active)
            throw NotificationConflitOnDeletionException(
                "Operation Failed: Is not possible to delete an active notification. \\" +
                        "You must turn it off to proceed deletion", HttpStatus.UNPROCESSABLE_ENTITY
            )

        repository.delete(notification)

        val deletionConfirmation = object {
            val message = """notificação de id=${notification.id} 
            criada por ${notification.author} foi deletada com sucesso"""".trimIndent()
        }

        return deletionConfirmation.message
    }

    fun toModel(request: NotificationRequest): Notification {
        return Notification(
            title = request.title,
            description = request.description,
            html = request.html,
            author = request.author,
            pinned = request.pinned,
            active = request.active,
            priority = request.priority,
            groups = request.groups
        )
    }

    fun toResponse(notification: Notification): NotificationResponse {
        return notification.copy() as NotificationResponse
    }
}