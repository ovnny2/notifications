package com.ovnny.notifications.controller

import com.ovnny.notifications.model.notifications.Notification
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
        notificationService.createNotification(notification)

        val location = URI.create("api/v1/notifications/${notification.id}")
        return ResponseEntity.created(location).body(notificationService.toResponse(notification))
    }

    @GetMapping("/api/v1/notifications")
    fun getAllNotifications(): ResponseEntity<List<NotificationResponse?>> {
        return ResponseEntity.ok().body(notificationService.getAllNotifications())
    }

    @GetMapping("/api/v1/notifications/{id}")
    fun getNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<Notification?> {
        val notificationResponse = notificationService.getNotification(id)
        return ResponseEntity.ok().body(notificationResponse)
    }

    @Transactional
    @PutMapping("/api/v1/notifications")
    fun updateNotification(@RequestBody @Valid alterations: Notification): ResponseEntity<NotificationResponse?> {
        return ResponseEntity.ok().body(notificationService.updateExistingNotification(alterations))
    }

    @Transactional
    @DeleteMapping("/api/v1/notifications/{id}")
    fun deleteNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<String> {
        return ResponseEntity.accepted().body(notificationService.deleteNotification(id))
    }

    @Transactional
    @PatchMapping("/api/v1/notifications/{id}")
    fun patchNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<String> {
        return ResponseEntity.accepted().body(notificationService.disableNotification(id))
    }
}