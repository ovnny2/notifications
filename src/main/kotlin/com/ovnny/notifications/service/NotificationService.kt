package com.ovnny.notifications.service

import com.ovnny.notifications.exception.NotificationConflitOnDeletionException
import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.model.notification.Notification
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
    fun createNotification(notification: Notification) {
        repository.save(notification)
    }

    fun getNotification(id: String): Notification {
        return repository.findById(id).orElseThrow { NotificationNotFoundException("", HttpStatus.NOT_FOUND) }.copy()
    }

    fun getAllNotifications(): List<NotificationResponse> {
        val notificationsList = repository.findAll() as List<Notification>
        return notificationsList.map { toResponse(it) }
    }

    @Transactional
    fun notificationStateToggle(id: String): String {

        val notification = repository.findById(id).orElseThrow {
            NotificationNotFoundException("", HttpStatus.NOT_FOUND)
        }

        val newState = !notification.active
        val notificationCopy = notification.copy(active = newState)
        repository.delete(notification)
        repository.save(notificationCopy)

        val response = object {
            val successStateChange = "the notification ${notificationCopy.id} state has changed to $newState"
        }

        return response.successStateChange
    }

    @Transactional
    fun updateExistingNotification(id: String, alterations: NotificationRequest): NotificationResponse? {
        val original = repository.findById(id).orElseThrow { NotificationNotFoundException("", HttpStatus.NOT_FOUND) }

        val updates = original.copy(
            title = alterations.title,
            description = alterations.description,
            html = alterations.html,
            author = original.author,
            pinned = alterations.pinned,
            active = original.active,
            priority = alterations.priority,
            groups = alterations.groups,
            createdAt = original.createdAt
        )

        repository.delete(original)
        repository.save(updates)

        return toResponse(updates)
    }

    @Transactional
    fun deleteNotification(id: String): String? {
        val notification = repository.findById(id).orElseThrow {
            NotificationNotFoundException(
                "A menssagem de id=$id não foi encontrada em nossos sistemas", HttpStatus.NOT_FOUND
            )
        }

        if (notification.active) throw NotificationConflitOnDeletionException("", HttpStatus.UNPROCESSABLE_ENTITY)

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

    fun toResponse(notification: Notification) = NotificationResponse(
        id = notification.id,
        title = notification.title,
        description = notification.description,
        html = notification.html,
        author = notification.author,
        pinned = notification.pinned,
        active = notification.active,
        priority = notification.priority,
        createdAt = notification.createdAt,
        updatedAt = notification.updatedAt
    )
}