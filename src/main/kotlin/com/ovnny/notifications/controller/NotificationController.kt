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
@RequestMapping("/v1")
class NotificationController(
    private val notificationService: NotificationService
) {

    @PostMapping("/notifications")
    fun createNotification(@RequestBody @Valid request: NotificationRequest): ResponseEntity<NotificationResponse> {
        val notification = notificationService.createNotification(request)
        val location = URI.create("api/notifications/${notification.id}")

        val response = notificationService.toResponse(notification)
        return ResponseEntity.created(location).body(response)
    }

    @GetMapping("/notifications/{id}")
    fun getNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<Notification?> {
        val notificationResponse = notificationService.getNotificationById(id)
        return ResponseEntity.ok().body(notificationResponse)
    }

    @GetMapping("/notifications")
    fun getAllNotifications(): ResponseEntity<List<NotificationResponse?>> {
        return ResponseEntity.ok().body(notificationService.getAllNotifications())
    }

    @PutMapping("/notifications/{id}")
    fun updateNotification(
        @PathVariable(value = "id") @Valid id: String,
        @RequestBody @Valid updates: NotificationRequest,
    ): ResponseEntity<NotificationResponse?> {
        return ResponseEntity.ok().body(notificationService.updateNotification(id, updates))
    }

    @PatchMapping("/notifications/{id}")
    fun toggleNotificationState(@PathVariable(value = "id") @Valid id: String): ResponseEntity<String> {
        return ResponseEntity.ok().body(notificationService.notificationStateToggle(id))
    }

    @DeleteMapping("/notifications/{id}")
    fun deleteNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<Unit> {
        notificationService.deleteNotification(id)

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}