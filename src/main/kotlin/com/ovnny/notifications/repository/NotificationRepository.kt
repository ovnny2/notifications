package com.ovnny.notifications.repository

import com.ovnny.notifications.model.notifications.Notification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : MongoRepository<Notification, String>