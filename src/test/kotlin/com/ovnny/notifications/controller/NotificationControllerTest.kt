package com.ovnny.notifications.controller

import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.model.notification.*
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

        val responseMock = createResponseNotificationMock(notification)

        `when`(notificationService.createNotification(request)).thenReturn(responseMock)

        val response = notificationController.createNotification(request)

        val mockResponse = createResponseNotificationMock(notification)

        assertAll(
            { assert(response.statusCode == HttpStatus.CREATED) },
            { assert(response.body!! == mockResponse) }
        )
    }

    @Test
    fun `should return NotificationNotFoundException for invalids notificationIds with status 404 NOT_FOUND`() {
        val exceptionMock = NotificationNotFoundException("", HttpStatus.NOT_FOUND)

        `when`(notificationService.getNotificationById(anyString())).thenThrow(exceptionMock)

        val exception = assertThrows<NotificationNotFoundException> {
            notificationService.getNotificationById(anyString())
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
        status = NotificationInfo(
            author = "ovnny",
            pinned = true,
            active = true,
            priority = "high",
            groups = listOf(Groups("1", "Admin")),
            parentId = null
        ),
        createdAt = LocalDateTime.of(2022, 11, 4, 12, 35, 45, 59),
        updatedAt = LocalDateTime.of(2022, 11, 4, 12, 35, 45, 59),

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

    private fun createResponseNotificationMock(notification: Notification) = NotificationResponse(
        id = notification.id,
        title = notification.title,
        description = notification.description,
        html = notification.html,
        author = null,
        pinned = notification.status.pinned,
        active = notification.status.active,
        priority = notification.status.priority,
        createdAt = notification.createdAt!!,
        lastUpdate = notification.createdAt!!,
        parentId = notification.status.parentId
    )
}