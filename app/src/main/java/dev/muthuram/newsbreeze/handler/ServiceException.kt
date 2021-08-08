package dev.muthuram.newsbreeze.handler




data class ServiceException(
    val code : String? = "Server error",
    val message: String? = "Something went wrong in the server. Please try again after some time",
)