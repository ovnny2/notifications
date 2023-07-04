package com.ovnny.notifications.controller

import com.ovnny.notifications.model.notification.Notification
import com.ovnny.notifications.model.notification.NotificationRequest
import com.ovnny.notifications.model.notification.NotificationResponse
import com.ovnny.notifications.service.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import jakarta.validation.Valid

@RestController
class NotificationController(
    private val notificationService: NotificationService,
) {

    @PostMapping("/api/v1/notifications")
    fun createNotification(@RequestBody @Valid request: NotificationRequest): ResponseEntity<NotificationResponse> {
        val notification = notificationService.createNotification(request)
        val location = URI.create("api/v1/notifications/${notification.id}")

        val response = notificationService.toResponse(notification)
        return ResponseEntity.created(location).body(response)
    }

    @GetMapping("/api/v1/notifications/{id}")
    fun getNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<Notification> {
        val notificationResponse = notificationService.getNotificationById(id)
        return ResponseEntity.ok().body(notificationResponse)
    }

    @GetMapping("/api/v1/notifications")
    fun getAllNotifications(): ResponseEntity<List<NotificationResponse?>> {
        return ResponseEntity.ok().body(notificationService.getAllNotifications())
    }

    @PutMapping("/api/v1/notifications/{id}")
    fun updateNotification(
        @PathVariable(value = "id") @Valid id: String,
        @RequestBody @Valid updates: NotificationRequest,
    ): ResponseEntity<NotificationResponse?> {
        return ResponseEntity.ok().body(notificationService.updateNotification(id, updates))
    }

    @PatchMapping("/api/v1/notifications/{id}")
    fun toggleNotificationState(@PathVariable(value = "id") @Valid id: String): ResponseEntity<String> {
        return ResponseEntity.ok().body(notificationService.notificationStateToggle(id))
    }

    @DeleteMapping("/api/v1/notifications/{id}")
    fun deleteNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<Unit> {
        notificationService.deleteNotification(id)

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}