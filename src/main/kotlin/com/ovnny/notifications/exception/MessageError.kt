package com.ovnny.notifications.exception

import org.springframework.http.HttpStatus

class MessageError(val message: String, val status: HttpStatus)