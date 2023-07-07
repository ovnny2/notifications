package com.ovnny.notifications.model.notification

import org.springframework.validation.annotation.Validated
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Validated
data class NotificationRequest(
    @get:NotBlank @get:Size(max = 64)
    val title: String,

    @get:NotBlank @get:Size(max = 256)
    val description: String,

    @Size(max = 1024)
    val html: String?,

    @get:NotBlank @get:Size(max = 32)
    val author: String,

    val pinned: Boolean? = false,

    @get:NotNull
    val active: Boolean,

    @get:NotBlank @get:Pattern(regexp = "high|medium|low")
    val priority: String,

    @get:NotEmpty
    val groups: List<Groups>
)