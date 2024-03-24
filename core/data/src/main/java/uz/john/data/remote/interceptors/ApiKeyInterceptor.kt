package uz.john.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import uz.john.data.BuildConfig

internal const val AUTHORIZATION = "Authorization"
internal const val BEARER = "Bearer"

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER ${BuildConfig.API_KEY}")
            .build()

        return chain.proceed(newRequest)
    }
}