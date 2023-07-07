package com.ovnny.notifications.service

import com.ovnny.notifications.model.notification.*
import com.ovnny.notifications.repository.NotificationRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.context.MessageSource
import java.time.LocalDateTime

class NotificationServiceTest {
    @Mock
    private val repository: NotificationRepository = mock(NotificationRepository::class.java)

    @Mock
    private val messageSource: MessageSource = mock(MessageSource::class.java)

    private val notificationService: NotificationService = NotificationService(repository, messageSource)


    @BeforeEach
    fun setUp() {
    }

    @Test
    fun createNotification() {

        val notification = Notification(
           id = "14e20de3-293e-413b-bbac-7333866320e5",
            title = "Test Notification",
            description = "This is a test notification",
            html = "<p>This is a test notification</p>",
            status = NotificationInfo(
                priority = "High",
                active = true,
                pinned = false,
                author = "John Doe",
                groups = listOf(Groups(id = "1", name = "Group 1"), Groups(id = "2", name = "Group 2"))
            ),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val expected = NotificationResponse(
            notification.id!!,
            notification.title,
            notification.description,
            notification.html,
            notification.status.author,
            notification.status.pinned,
            notification.status.active,
            notification.status.priority,
            notification.createdAt!!,
            notification.updatedAt!!
        )

        `when`(repository.save(notification)).thenReturn(notification)

        val actual = notificationService.createNotification(notification)

        Assertions.assertEquals(actual, expected)
    }

    @Test
    fun getNotificationById() {
    }

    @Test
    fun getAllNotifications() {
    }

    @Test
    fun toggleNotificationState() {
    }

    @Test
    fun updateNotification() {
    }

    @Test
    fun deleteNotification() {
    }

}