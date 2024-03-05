package uz.john.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import uz.john.data.BuildConfig
import uz.john.data.remote.AUTHORIZATION
import uz.john.data.remote.BEARER

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER ${BuildConfig.API_KEY}")
            .build()

        return chain.proceed(newRequest)
    }
}