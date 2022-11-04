package com.ovnny.notifications.controller

import com.ovnny.notifications.model.notifications.NotificationRequest
import com.ovnny.notifications.model.notifications.NotificationResponse
import com.ovnny.notifications.repository.NotificationRepository
import com.ovnny.notifications.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
class NotificationController(
    val notificationService: NotificationService,
    val notificationRepository: NotificationRepository
) {

    @Transactional
    @PostMapping("/api/v1/notifications")
    fun createNotification(@RequestBody @Valid request: NotificationRequest): ResponseEntity<NotificationResponse> {
        val notification = notificationService.toModel(request)
        notificationRepository.save(notification)

        val location = URI.create("api/v1/notifications/${notification.id}")
        return ResponseEntity.created(location).body(notificationService.toResponse(notification))
    }
}
