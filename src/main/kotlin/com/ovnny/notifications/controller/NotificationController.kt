package com.ovnny.notifications.controller

import com.ovnny.notifications.model.notifications.NotificationRequest
import com.ovnny.notifications.model.notifications.NotificationResponse
import com.ovnny.notifications.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
class NotificationController(
    val notificationService: NotificationService,
) {

    @PostMapping("/api/v1/notifications")
    fun createNotification(@RequestBody @Valid request: NotificationRequest): ResponseEntity<NotificationResponse> {
        val notification = notificationService.toModel(request)
        notificationService.saveNotification(notification)

        val location = URI.create("api/v1/notifications/${notification.id}")
        return ResponseEntity.created(location).body(notificationService.toResponse(notification))
    }

    @GetMapping("/api/v1/notifications")
    fun getAllNotifications(): ResponseEntity<List<NotificationResponse?>> {
        return ResponseEntity.ok().body(notificationService.getAllNotifications())
    }

    @GetMapping("/api/v1/notifications/{id}")
    fun getNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<NotificationResponse?> {
        val notification = notificationService.getNotification(id)
        return ResponseEntity.ok().body(notificationService.toResponse(notification))
    }

    @Transactional
    @PutMapping("/api/v1/notifications")
    fun updateNotification(@RequestBody @Valid request: NotificationRequest): ResponseEntity<NotificationResponse?> {
        val notification = notificationService.toModel(request)
        return ResponseEntity.ok().body(notificationService.updateExistingNotification(notification))
    }
}