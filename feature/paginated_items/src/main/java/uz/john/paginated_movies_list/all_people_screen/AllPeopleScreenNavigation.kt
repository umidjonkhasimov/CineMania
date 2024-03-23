package uz.john.paginated_movies_list.all_people_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val ALL_PEOPLE_ARG = "ALL_PEOPLE_ARG"
const val ALL_PEOPLE = "ALL_PEOPLE"
const val ALL_PEOPLE_ROUTE = "$ALL_PEOPLE/{$ALL_PEOPLE_ARG}"


fun NavController.navigateToAllPeopleScreen(
    allPeopleScreenParam: AllPeopleScreenParam,
    navOptions: NavOptions? = null
) {
    val jsonString = Json.encodeToString(allPeopleScreenParam)
    navigate(
        route = "$ALL_PEOPLE/$jsonString",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.allPeopleScreen(
    onPersonClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable(route = ALL_PEOPLE_ROUTE) {
        AllPeopleScreen(
            onPersonClick = onPersonClick,
            onBackClick = onBackClick
        )
    }
}