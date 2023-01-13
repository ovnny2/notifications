package com.ovnny.notifications.repository

import com.ovnny.notifications.model.notification.Notification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : MongoRepository<Notification, String> {
    fun findAllByStatusParentId(id: String): MutableList<Notification>
}