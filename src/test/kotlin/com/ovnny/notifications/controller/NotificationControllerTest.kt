package com.ovnny.notifications.controller

import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.model.notification.Groups
import com.ovnny.notifications.model.notification.Notification
import com.ovnny.notifications.model.notification.NotificationRequest
import com.ovnny.notifications.model.notification.NotificationResponse
import com.ovnny.notifications.service.NotificationService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class NotificationControllerTest {

    @InjectMocks
    lateinit var notificationController: NotificationController

    @Mock
    val notificationService: NotificationService = mock(NotificationService::class.java)

    @Test
    fun `should return a new notification with status OK`() {
        val request = createRequestNotificationMock()

        val notification = createNotificationMock()

        val notificationResponse = createResponseNotificationMock()

        `when`(notificationService.toModel(request)).thenReturn(notification)
        `when`(notificationService.toResponse(notification)).thenReturn(notificationResponse)

        val response = notificationController.createNotification(request)

        assertAll(
            { assert(response.statusCode == HttpStatus.CREATED) },
            { assert(response.body!! == notificationResponse) }
        )
    }

    @Test
    fun `should return NotificationNotFoundException for invalids notificationIds with status 404 NOT_FOUND`() {
        val exceptionMock = NotificationNotFoundException("", HttpStatus.NOT_FOUND)

        `when`(notificationService.getNotification(anyString())).thenThrow(exceptionMock)

        val exception = assertThrows<NotificationNotFoundException> {
            notificationService.getNotification(anyString())
        }

        assertAll(
            { assert(exceptionMock.equals(exception)) },
            { assert(exceptionMock.message.equals(exception.message)) }
        )
    }

    private fun createNotificationMock() = Notification(
        title = "Criando um mock de Notification",
        description = "Teste Unitário com JUnit5",
        html = "<body><p>Esse é um teste unitário de criação de Notifications</p></body>",
        author = "ovnny",
        pinned = true,
        active = true,
        priority = "high",
        groups = listOf(Groups("1", "Admin")),
        createdAt = LocalDateTime.of(2022, 11, 4, 12, 35, 45, 59),
        updatedAt = LocalDateTime.of(2022, 11, 4, 12, 35, 45, 59)
    )

    private fun createRequestNotificationMock() = NotificationRequest(
        title = "Criando um mock de Notification",
        description = "Teste Unitário com JUnit5",
        html = "<body><p>Esse é um teste unitário de criação de Notifications</p></body>",
        author = "ovnny",
        pinned = true,
        active = true,
        priority = "high",
        groups = listOf(Groups("1", "Admin"))
    )

    private fun createResponseNotificationMock() = NotificationResponse(
        id = createNotificationMock().id,
        title = "Criando um mock de Notification",
        description = "Teste Unitário com JUnit5",
        html = "<body><p>Esse é um teste unitário de criação de Notifications</p></body>",
        author = null,
        pinned = true,
        active = true,
        priority = "high",
        updatedAt = LocalDateTime.of(2022, 11, 4, 12, 35, 45, 59),
        createdAt = LocalDateTime.of(2022, 11, 4, 12, 35, 45, 59)
    )
}