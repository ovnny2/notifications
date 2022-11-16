package com.ovnny.notifications.model.notification

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Validated
data class NotificationRequest(
    @get:NotBlank @get:Size(max = 64)
    val title: String,

    @get:NotBlank @get:Size(max = 256)
    val description: String,

    val html: String?,

    @get:NotBlank @get:Size(max = 32)
    val author: String,

    val pinned: Boolean,

    @get:NotNull
    val active: Boolean,

    @get:NotBlank @get:Pattern(regexp = "high|medium|low")
    val priority: String,

    @get:NotEmpty
    val groups: List<Groups>,
)