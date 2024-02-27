package uz.john.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.john.onboarding.OnboardingScreen

const val ONBOARDING_ROUTE = "ONBOARDING_ROUTE"

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    navigate(route = ONBOARDING_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.onboardingScreen(
    onStartClick: () -> Unit,
) {
    composable(route = ONBOARDING_ROUTE) {
        OnboardingScreen(
            onStartClick = onStartClick,
        )
    }
}