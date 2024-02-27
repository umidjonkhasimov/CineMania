package uz.john.util

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class ResultModel<T : Any> {
    class Success<T : Any>(val data: T) : ResultModel<T>()
    class Error<T : Any>(val error: NetworkError, val data: T? = null) : ResultModel<T>()
    class Exception<T : Any>(val throwable: Throwable, val data: T? = null) : ResultModel<T>()
}

suspend fun <T : Any> invokeRequest(
    invoke: suspend () -> Response<T>
): ResultModel<T> {
    return try {
        val result = invoke()
        val body = result.body()

        if (result.isSuccessful && body != null) {
            ResultModel.Success(data = body)
        } else {
            val gson = Gson()
            val errorBody = result.errorBody()?.string()
            val errorCode = result.code()
            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

            ResultModel.Error(
                error = NetworkError(
                    errorMessage = errorResponse.statusMessage,
                    code = errorCode
                )
            )
        }
    } catch (e: HttpException) {
        return ResultModel.Exception(throwable = e)
    } catch (t: Throwable) {
        return ResultModel.Exception(throwable = t)
    }
}

data class NetworkError(
    val errorMessage: String,
    val code: Int
) : IOException()

data class ErrorResponse(
    val success: Boolean,
    @SerializedName(value = "status_code")
    val statusCode: Int,
    @SerializedName(value = "status_message")
    val statusMessage: String
)