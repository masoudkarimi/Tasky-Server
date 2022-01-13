package info.masoudkarimi.tasky.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("status")
    val status: Boolean,

    @SerialName("message")
    val message: String? = null,

    @SerialName("errorMessage")
    val errorMessage: String? = null,

    @SerialName("data")
    val data: T? = null
)

fun <T> successResponse(data: T) = BaseResponse(
    status = true,
    data = data
)

fun errorResponse(error: String) = BaseResponse<String>(
    status = false,
    errorMessage = error
)
