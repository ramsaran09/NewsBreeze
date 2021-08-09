package dev.muthuram.newsbreeze.handler

import dev.muthuram.newsbreeze.constants.ERROR_MESSAGE
import dev.muthuram.newsbreeze.constants.SERVER_ERROR


data class ServiceException(
    val code : String? = SERVER_ERROR,
    val message: String? = ERROR_MESSAGE,
)