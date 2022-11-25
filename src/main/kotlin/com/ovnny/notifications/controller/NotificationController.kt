package com.ovnny.notifications.controller

import com.ovnny.notifications.model.notification.Notification
import com.ovnny.notifications.model.notification.NotificationRequest
import com.ovnny.notifications.model.notification.NotificationResponse
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

    @Transactional
    @PostMapping("/api/v1/notifications")
    fun createNotification(@RequestBody @Valid request: NotificationRequest): ResponseEntity<NotificationResponse> {
        val notification = notificationService.toModel(request)
        notificationService.createNotification(notification)

        val location = URI.create("api/v1/notifications/${notification.id}")
        return ResponseEntity.created(location).body(notificationService.toResponse(notification))
    }

    @GetMapping("/api/v1/notifications/{id}")
    fun getNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<Notification?> {
        val notificationResponse = notificationService.getNotification(id)
        return ResponseEntity.ok().body(notificationResponse)
    }

    @GetMapping("/api/v1/notifications")
    fun getAllNotifications(): ResponseEntity<List<NotificationResponse?>> {
        return ResponseEntity.ok().body(notificationService.getAllNotifications())
    }

    @Transactional
    @PutMapping("/api/v1/notifications/{id}")
    fun updateNotification(
        @PathVariable(value = "id") @Valid id: String,
        @RequestBody @Valid request: NotificationRequest,
    ): ResponseEntity<NotificationResponse?> {
        return ResponseEntity.ok().body(notificationService.updateExistingNotification(id, request))
    }

    @Transactional
    @PatchMapping("/api/v1/notifications/{id}")
    fun patchNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<String> {
        return ResponseEntity.accepted().body(notificationService.notificationStateToggle(id))
    }

    @Transactional
    @DeleteMapping("/api/v1/notifications/{id}")
    fun deleteNotification(@PathVariable(value = "id") @Valid id: String): ResponseEntity<String> {
        return ResponseEntity.accepted().body(notificationService.deleteNotification(id))
    }
}