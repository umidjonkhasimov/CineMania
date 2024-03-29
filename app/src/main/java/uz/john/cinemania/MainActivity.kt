package uz.john.cinemania

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import uz.john.domain.model.UiMode
import uz.john.ui.theme.CineManiaTheme
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()
            val uiState by viewModel.userData.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition { uiState.isLoading }

            LaunchedEffect(uiState.userData?.language) {
                updateResources(uiState.userData?.language?.locale ?: "ru")
            }

            uiState.userData?.let { userData ->
                CineManiaTheme(
                    darkTheme = when (userData.uiMode) {
                        UiMode.SYSTEM -> isSystemInDarkTheme()
                        UiMode.LIGHT -> false
                        UiMode.DARK -> true
                    }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CineManiaNavHost(
                            isOnboarded = userData.isOnboarded,
                            isLoggedIn = userData.isLoggedIn,
                            setOnboarded = {
                                viewModel.setIsOnboarded(it)
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun Activity.updateResources(language: String) {
    resources.apply {
        val locale = Locale(language)
        val config = Configuration(configuration)

        createConfigurationContext(configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, displayMetrics)
    }
}