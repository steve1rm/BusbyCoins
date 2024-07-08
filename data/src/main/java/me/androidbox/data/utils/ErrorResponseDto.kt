package me.androidbox.data.utils

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val errors: List<ErrorDto>
)