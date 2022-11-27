package com.ovnny.notifications.exception.msg

enum class NotificationMessages(val msg: String) {


    GENERIC_MESSAGE("Houve um erro durante o processamento da notificação. Por favor, tente novamente"),

    NOT_FOUND_MESSAGE("Não foram encontradas notificações com o id informado.\n" +
            "Confira se a informação está correta e tente novamente"
    ),

    USER_INPUT_ERROR("O campo tem um formato insatisfatório"),

    RULE_BUSINESS_BROKEN_MESSAGE("Houve falha na operação devido estado restritivo do objeto. " +
            "Operação não permitida."),

    MODIFY_ACTIVE_NOTIFICATION_NOT_ALLOWED(
        "Operation Failed: Is not possible to delete an active notification.\n" +
            "Disable it first to proceed deletion"
    )
}