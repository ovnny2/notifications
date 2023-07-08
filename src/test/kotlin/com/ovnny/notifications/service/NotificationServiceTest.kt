package com.ovnny.notifications.service

import com.ovnny.notifications.exception.NotificationNotFoundException
import com.ovnny.notifications.model.notification.*
import com.ovnny.notifications.repository.NotificationRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.MessageSource
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class NotificationServiceTest {

    @Mock
    private val repository: NotificationRepository = mock(NotificationRepository::class.java)

    @Mock
    private val messageSource: MessageSource = mock(MessageSource::class.java)

    @InjectMocks
    private lateinit var notificationService: NotificationService


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

        assertEquals(actual, expected)
    }

    @Test
    fun getNotificationById() {

        val id = "14e20de3-293e-413b-bbac-7333866320e5"

        val expectedNotification = Notification(
            id = id,
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

        `when`(repository.findById(expectedNotification.id!!)).thenReturn(Optional.of(expectedNotification))

        val actual: Notification = notificationService.getNotificationById(id)

        assertEquals(expectedNotification, actual)
    }

    @Test
    fun `should return NotfoundException when the given id was not founded`() {

        val id = "14e20de3-293e-413b-bbac-7333866320e5"

        assertThrows(NotificationNotFoundException::class.java) {
            notificationService.getNotificationById(id)
        }
    }

    @Test
    fun getAllNotifications() {

        val notification1 = Notification(
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

        val notification2 = Notification(
            id = "24e20de3-293e-413b-bbac-7333636320e5",
            title = "Test Notification",
            description = "This is a test notification",
            html = "<p>This is a test notification</p>",
            status = NotificationInfo(
                priority = "Medium",
                active = true,
                pinned = false,
                author = "Louis Lanne",
                groups = listOf(Groups(id = "3", name = "Group 3"), Groups(id = "4", name = "Group 4"))
            ),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val notificationList = mutableListOf(notification1, notification2)

        val expected = notificationList.map { notificationService.toResponse(it) }

        `when`(repository.findAll()).thenReturn(notificationList)

        val result = notificationService.getAllNotifications()

        assertAll(
            { assertEquals(expected, result) },
            { assertTrue(result.size == expected.size) },
            { assertTrue(result::class.java == expected::class.java) }
        )
    }

    @Test
    fun `should change the 'active' notification's state to 'inactive'`() {

        val id = "14e20de3-293e-413b-bbac-7333866320e5"

        val notification = Notification(
            id = id,
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

        val expectedMessage = "Notificação [ ${notification.title} ] desativada com sucesso."

        `when`(
            messageSource.getMessage(
                "InactiveNotification", arrayOf(notification.title), Locale("pt")
            )
        ).thenReturn(expectedMessage)

        `when`(repository.findById(id)).thenReturn(Optional.of(notification))

        `when`(repository.save(notification)).thenReturn(notification)

        val result = notificationService.toggleNotificationState(id)

        assertEquals(expectedMessage, result)
        assertEquals(false, notification.status.active)
    }

    @Test
    fun `should change the 'inactive' notification's state to 'active'`() {

        val id = "14e20de3-293e-413b-bbac-7333866320e5"

        val notification = Notification(
            id = id,
            title = "Test Notification",
            description = "This is a test notification",
            html = "<p>This is a test notification</p>",
            status = NotificationInfo(
                priority = "High",
                active = false,
                pinned = false,
                author = "John Doe",
                groups = listOf(Groups(id = "1", name = "Group 1"), Groups(id = "2", name = "Group 2"))
            ),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val expectedMessage = "Notificação [ ${notification.title} ] desativada com sucesso."

        `when`(
            messageSource.getMessage(
                "ActiveNotification", arrayOf(notification.title), Locale("pt")
            )
        ).thenReturn(expectedMessage)

        `when`(repository.findById(id)).thenReturn(Optional.of(notification))

        `when`(repository.save(notification)).thenReturn(notification)

        val result = notificationService.toggleNotificationState(id)

        assertEquals(expectedMessage, result)
        assertEquals(true, notification.status.active)
    }

    @Test
    fun updateNotification() {
        val originalNotification = Notification(
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

        val updateNotificationRequest = NotificationRequest(
            title = "Updated Test Notification",
            description = "This notification was updated",
            html = "<p>This is a test notification</p>",
            author = "John Doe",
            pinned = false,
            active = true,
            priority = "Medium",
            groups = listOf(Groups(id = "1", name = "Group 1"), Groups(id = "2", name = "Group 2"))
        )

        val expected = NotificationResponse(
            originalNotification.id!!,
            updateNotificationRequest.title,
            updateNotificationRequest.description,
            updateNotificationRequest.html,
            updateNotificationRequest.author,
            updateNotificationRequest.pinned!!,
            updateNotificationRequest.active,
            updateNotificationRequest.priority,
            originalNotification.createdAt!!,
            originalNotification.updatedAt!!
        )

        `when`(repository.findById(originalNotification.id!!)).thenReturn(Optional.of(originalNotification))

        `when`(repository.save(originalNotification)).thenReturn(originalNotification)

        `when`(repository.save(originalNotification.copy(
            title = updateNotificationRequest.title,
            description = updateNotificationRequest.description,
            html = updateNotificationRequest.html,
            status = originalNotification.status.copy(
                author = updateNotificationRequest.author,
                pinned = updateNotificationRequest.pinned ?: originalNotification.status.pinned,
                active = updateNotificationRequest.active,
                priority = updateNotificationRequest.priority,
                groups = updateNotificationRequest.groups)))

        ).thenReturn(originalNotification.copy(
            title = updateNotificationRequest.title,
            description = updateNotificationRequest.description,
            html = updateNotificationRequest.html,
            status = originalNotification.status.copy(
                author = updateNotificationRequest.author,
                pinned = updateNotificationRequest.pinned ?: originalNotification.status.pinned,
                active = updateNotificationRequest.active,
                priority = updateNotificationRequest.priority,
                groups = updateNotificationRequest.groups))
        )

        val result = notificationService.updateNotification(originalNotification.id!!, updateNotificationRequest)

        assertEquals(expected, result)
        assertEquals(false, originalNotification.status.active)

    }

    @Test
    fun `test deleteNotification - existing notification`() {
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

        `when`(repository.findById(notification.id!!)).thenReturn(Optional.of(notification))

        notificationService.deleteNotification(notification.id!!)

        verify(repository).delete(notification)
    }


    @Test
    fun `test deleteNotification - notification not found`() {
        val id = "non_existent_id"

        `when`(repository.findById(id)).thenReturn(Optional.empty())

        assertThrows(NotificationNotFoundException::class.java) {
            notificationService.deleteNotification(id)
        }
    }
}