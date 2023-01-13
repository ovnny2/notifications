package com.ovnny.notifications.service

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
    private val repository: NotificationRepository
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

        val notificationUpdatedStatus = notification.status.copy(
            active = !notification.status.active
        )

        val newNotification = repository.save(
            notification.copy(
                status = notificationUpdatedStatus
            )
        )

        return when (newNotification.status.active) {
            true -> "A notificação foi ativada com sucesso"
            else -> "A notificação foi desativada com sucesso"
        }
    }

    @Transactional
    fun updateNotification(id: String, updateRequest: NotificationRequest): NotificationResponse? {

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

        original.status.active = false

        repository.save(original)
        repository.save(updates)

        return toResponse(updates)
    }

    @Transactional
    fun deleteNotification(id: String) {

        val possibleChild = repository.findById(id).orElseThrow {
            NotificationNotFoundException(
                NotificationMessages.NOT_FOUND_MESSAGE.msg,
                HttpStatus.NOT_FOUND
            )
        }

        val idList = mutableListOf<String>()
        idList.add(possibleChild.id)

        val history = repository.findAllByStatusParentId(id)
        for (not in history) idList.add(not.id)


        repository.deleteAllById(idList)
        println(idList.toString())
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
                groups = request.groups,
                parentId = null
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