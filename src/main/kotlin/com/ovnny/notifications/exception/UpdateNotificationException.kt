package com.ovnny.notifications.exception

import java.lang.IllegalArgumentException

class UpdateNotificationException(
    msg: String? = "Os parâmetros para atualização são iguais aos parâmetros da mensagem existente"
): IllegalArgumentException(msg)