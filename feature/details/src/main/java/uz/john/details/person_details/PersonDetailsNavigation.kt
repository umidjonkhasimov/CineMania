package uz.john.details.person_details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val PERSON_DETAILS_ARG = "PERSON_DETAILS_ARG"
const val PERSON_DETAILS = "PERSON_DETAILS"
const val PERSON_DETAILS_ROUTE = "$PERSON_DETAILS/{$PERSON_DETAILS_ARG}"

fun NavController.navigateToPersonDetailsScreen(personId: Int, navOptions: NavOptions? = null) {
    navigate(
        route = "$PERSON_DETAILS/$personId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.personDetailsScreen(
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMovieItemClick: (Int) -> Unit,
    onTvShowItemClick: (Int) -> Unit
) {
    composable(route = PERSON_DETAILS_ROUTE) {
        PersonDetailsScreen(
            onBackClick = onBackClick,
            onImageClick = onImageClick,
            onMovieItemClick = onMovieItemClick,
            onTvShowItemClick = onTvShowItemClick
        )
    }
}