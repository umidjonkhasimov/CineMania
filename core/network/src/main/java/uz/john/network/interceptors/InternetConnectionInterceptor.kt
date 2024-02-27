package uz.john.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import uz.john.network.R
import uz.john.network.exceptions.NoConnectionException
import javax.inject.Inject

class InternetConnectionInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectionException(context.getString(R.string.no_network_available))
        }
        return chain.proceed(chain.request())
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService<ConnectivityManager>() ?: return false

        return cm.getNetworkCapabilities(cm.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
}