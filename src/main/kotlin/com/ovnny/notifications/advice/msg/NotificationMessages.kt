package com.ovnny.notifications.advice.msg

enum class NotificationMessages(val msg: String) {
    GENERIC_MESSAGE("Houve um erro durante o processamento da notificação. Por favor, tente novamente"),

    NOT_FOUND_MESSAGE("Não foram encontradas notificações com o id informado. " +
            "Confira se a informação está correta e tente novamente"),

    RULE_BUSINESS_BROKEN_MESSAGE("Houve falha na operação devido estado restritivo do objeto. Operação não permitida.")
}