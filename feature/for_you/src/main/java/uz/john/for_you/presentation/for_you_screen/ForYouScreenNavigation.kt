package uz.john.for_you.presentation.for_you_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val FOR_YOU_ROUTE = "FOR_YOU_ROUTE"

fun NavController.navigateToForYouScreen(navOptions: NavOptions? = null) {
    navigate(FOR_YOU_ROUTE, navOptions)
}

fun NavGraphBuilder.forYouScreen() {
    composable(route = FOR_YOU_ROUTE) {
        ForYouScreen()
    }
}