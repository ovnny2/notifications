package com.ovnny.notifications.model.notifications

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Validated
data class NotificationRequest(
    @NotBlank @Size(max = 64)
    val title: String,
    @NotBlank @Size(max = 256)
    val description: String,
    val html: String?,
    @NotBlank @Size(max = 32)
    val author: String,
    @NotBlank @Size(min = 1)
    val groups: List<Groups>
)
