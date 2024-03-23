package uz.john.cinemania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import uz.john.ui.theme.CineManiaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()
            val uiState = viewModel.userData.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition {
                uiState.value is UiState.Loading
            }

            when (uiState.value) {
                is UiState.Success -> {
                    CineManiaTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            CineManiaNavHost(
                                isOnboarded = (uiState.value as UiState.Success).userData.isOnboarded,
                                isLoggedIn = (uiState.value as UiState.Success).userData.isLoggedIn,
                                setOnboarded = {
                                    viewModel.setIsOnboarded(it)
                                }
                            )
                        }
                    }
                }

                UiState.Loading -> {}
            }
        }
    }
}