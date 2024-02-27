package uz.john.authentication.presentation.sign_in_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SIGN_IN_ROUTE = "SIGN_IN_ROUTE"

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    navigate(SIGN_IN_ROUTE, navOptions)
}

fun NavGraphBuilder.signInScreen(
    onBackClick: () -> Unit,
    onNavigateToMainScreen: () -> Unit
) {
    composable(SIGN_IN_ROUTE) {
        SignInScreen(
            onBackClick = onBackClick,
            onNavigateToMainScreen = onNavigateToMainScreen
        )
    }
}