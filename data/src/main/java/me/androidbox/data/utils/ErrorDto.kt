package me.androidbox.data.utils

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val status: String,
    val type: String,
    val message: String
)