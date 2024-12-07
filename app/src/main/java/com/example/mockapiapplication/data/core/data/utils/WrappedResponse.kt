package com.example.mockapiapplication.data.core.data.utils

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    var code: Int,
    var status: String,
    var data: T
)

data class WrappedErrorResponse(
    @SerializedName("error") var errorResponse: ErrorResponse,
)

data class ErrorResponse(
    val message: String
)