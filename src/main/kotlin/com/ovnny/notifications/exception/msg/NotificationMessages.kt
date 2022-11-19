package com.ovnny.notifications.exception.msg

enum class NotificationMessages(val msg: String) {


    GENERIC_MESSAGE("Houve um erro durante o processamento da notificação. Por favor, tente novamente"),

    NOT_FOUND_MESSAGE("Não foram encontradas notificações com o id informado Por favor, confira se o parâmetro está correto e tente novamente"),

    USER_INPUT_ERROR("O campo tem um formato insatisfatório"),

    RULE_BUSINESS_BROKEN_MESSAGE("Houve falha na operação devido à O processamento não foi realizado devido à")
}