package uz.john.profile.presentation.profile_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val PROFILE_SCREEN = "PROFILE_SCREEN"

fun NavController.navigateToProfileScreen(navOptions: NavOptions? = null) {
    navigate(PROFILE_SCREEN, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onSignInClick: () -> Unit
) {
    composable(route = PROFILE_SCREEN) {
        ProfileScreen(
            onSignInClick = onSignInClick
        )
    }
}