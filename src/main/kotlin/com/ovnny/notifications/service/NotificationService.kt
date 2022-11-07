package com.ovnny.notifications.service

import com.ovnny.notifications.exception.NotificationConflitOnDeletionException
import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.model.notifications.Notification
import com.ovnny.notifications.model.notifications.NotificationRequest
import com.ovnny.notifications.model.notifications.NotificationResponse
import com.ovnny.notifications.repository.NotificationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional
import javax.validation.Valid

@Service
class NotificationService(
    val repository: NotificationRepository,
) {

    fun getNotification(id: String): Notification {
        return repository.findById(id).orElseThrow { NotificationNotFoundException() }
            .copy()
    }

    fun getAllNotifications(): List<NotificationResponse> {
        val notificationsList = repository.findAll() as List<Notification>
        return notificationsList.map { toResponse(it) }
    }

    @Transactional
    fun createNotification(notification: Notification) {
        repository.save(notification)
    }

    @Transactional
    fun updateExistingNotification(alterations: Notification): NotificationResponse? {

        val original = repository.findById(alterations.id as String).orElseThrow { NotificationNotFoundException() }

        original
            .apply { active to false }
            .apply { updatedAt to LocalDateTime.now() }
            .also { repository.save(it) }

        return toResponse(alterations)
    }

    fun deleteNotification(id: String): String? {
        val notification = repository.findById(id).orElseThrow { NotificationNotFoundException() }
        if (notification.active) throw NotificationConflitOnDeletionException()

        repository.delete(notification)

        val deletionConfirmation = object {
            val message = """notificação de 'id' ${notification.id} 
            criada por ${notification.author} foi deletada com sucesso"""".trimMargin()
        }

        return deletionConfirmation.message
    }

    @Transactional
    fun disableNotification(id: String): String? {
        runCatching {

            val notification = repository.findById(id).get()
            val notificationCopy = notification.copy(active = false)
            repository.delete(notification)
            repository.save(notificationCopy)

            val disableConfirmation = object {
                val message = "A notificação $id foi desativada com sucesso"
            }

            return disableConfirmation.message

        }.getOrElse { throw NotificationNotFoundException() }
    }

    fun toModel(@Valid request: NotificationRequest) = Notification(
        title = request.title,
        description = request.description,
        html = request.html,
        author = request.author,
        pinned = request.pinned,
        active = request.active ?: false,
        priority = request.priority,
        groups = request.groups
    )

    fun toResponse(notification: Notification) = NotificationResponse(
        notification.id!!,
        notification.title,
        notification.description,
        notification.html,
        notification.author,
        notification.pinned,
        notification.active,
        notification.priority,
        notification.createdAt,
        notification.updatedAt
    )
}