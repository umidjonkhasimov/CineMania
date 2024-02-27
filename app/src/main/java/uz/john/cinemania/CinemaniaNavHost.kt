package uz.john.cinemania

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import uz.john.authentication.presentation.sign_in_screen.navigateToSignIn
import uz.john.authentication.presentation.sign_in_screen.signInScreen
import uz.john.authentication.presentation.welcome.WELCOME_ROUTE
import uz.john.authentication.presentation.welcome.navigateToWelcomeScreen
import uz.john.authentication.presentation.welcome.welcomeScreen
import uz.john.onboarding.navigation.ONBOARDING_ROUTE
import uz.john.onboarding.navigation.onboardingScreen

@Composable
fun CineManiaNavHost(
    isOnboarded: Boolean,
    isLoggedIn: Boolean,
    setOnboarded: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val startDestination = if (!isOnboarded)
        ONBOARDING_ROUTE
    else if (!isLoggedIn)
        WELCOME_ROUTE
    else
        "" // TODO("Add Main Screen Navigation")

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally { it }
        },
        exitTransition = {
            slideOutHorizontally { -it }
        },
        popEnterTransition = {
            slideInHorizontally { -it }
        },
        popExitTransition = {
            slideOutHorizontally { it }
        }
    ) {

        onboardingScreen(
            onStartClick = {
                setOnboarded(true)
                navController.navigateToWelcomeScreen()
            },
        )

        welcomeScreen(
            onSignUpClick = {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("https://www.themoviedb.org/signup")
                }
                context.startActivity(intent)
            },
            onContinueWithoutAccountClick = {
                // TODO: (Navigate to home screen)
            },
            onLoginClick = {
                navController.navigateToSignIn()
            }
        )

        signInScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onNavigateToMainScreen = {
                // TODO: (Navigate to home screen)
            }
        )
    }
}