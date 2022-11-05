package com.ovnny.notifications.exception

import java.lang.IllegalArgumentException

class UpdateNotificationException(
    override val message: String? = "Os parâmetros para atualização são iguais aos parâmetros da mensagem existente"
): IllegalArgumentException(message)