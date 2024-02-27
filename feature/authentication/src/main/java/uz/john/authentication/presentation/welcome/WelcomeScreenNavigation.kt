package uz.john.authentication.presentation.welcome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val WELCOME_ROUTE = "WELCOME_ROUTE"

fun NavController.navigateToWelcomeScreen(navOptions: NavOptions? = null) {
    navigate(WELCOME_ROUTE, navOptions)
}

fun NavGraphBuilder.welcomeScreen(
    onSignUpClick: () -> Unit,
    onContinueWithoutAccountClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    composable(WELCOME_ROUTE) {
        WelcomeScreen(
            onSignUpClick = onSignUpClick,
            onContinueWithoutAccountClick = onContinueWithoutAccountClick,
            onLoginClick = onLoginClick
        )
    }
}