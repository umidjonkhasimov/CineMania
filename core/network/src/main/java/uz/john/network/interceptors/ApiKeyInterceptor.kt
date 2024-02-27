package uz.john.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import uz.john.network.AUTHORIZATION
import uz.john.network.BEARER
import uz.john.network.BuildConfig

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER ${BuildConfig.API_KEY}")
            .build()

        return chain.proceed(newRequest)
    }
}