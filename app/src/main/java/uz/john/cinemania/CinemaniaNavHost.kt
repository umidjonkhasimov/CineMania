package uz.john.cinemania

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import uz.john.authentication.presentation.sign_in_screen.navigateToSignIn
import uz.john.authentication.presentation.sign_in_screen.signInScreen
import uz.john.authentication.presentation.welcome.WELCOME_ROUTE
import uz.john.authentication.presentation.welcome.navigateToWelcomeScreen
import uz.john.authentication.presentation.welcome.welcomeScreen
import uz.john.cinemania.image_viewer.imageViewerScreen
import uz.john.cinemania.image_viewer.navigateToImageViewerScreen
import uz.john.cinemania.main_screen.MAIN_ROUTE
import uz.john.cinemania.main_screen.mainScreen
import uz.john.cinemania.main_screen.navigateToMainScreen
import uz.john.details.movie_details_screen.movieDetailsScreen
import uz.john.details.movie_details_screen.navigateToMovieDetailsScreen
import uz.john.details.person_details.navigateToPersonDetailsScreen
import uz.john.details.person_details.personDetailsScreen
import uz.john.details.tv_show_details.navigateToTvShowsDetailsScreen
import uz.john.details.tv_show_details.tvShowDetailsScreen
import uz.john.onboarding.navigation.ONBOARDING_ROUTE
import uz.john.onboarding.navigation.onboardingScreen
import uz.john.paginated_movies_list.all_movies_screen.allMoviesScreen
import uz.john.paginated_movies_list.all_movies_screen.navigateToAllMoviesScreen
import uz.john.paginated_movies_list.all_people_screen.allPeopleScreen
import uz.john.paginated_movies_list.all_people_screen.navigateToAllPeopleScreen
import uz.john.paginated_movies_list.all_tv_shows_screen.allTvShowsScreen
import uz.john.paginated_movies_list.all_tv_shows_screen.navigateToAllTvShowsScreen

private const val TMDB_SIGN_IN_PAGE = "https://www.themoviedb.org/signup"
private const val NAVIGATION_ANIMATION_DURATION = 400

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
        MAIN_ROUTE

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            scaleIn(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION),
                initialScale = .95f,
            ) + fadeIn(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
            )
        },
        exitTransition = {
            scaleOut(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION),
                targetScale = .95f,
            ) + fadeOut(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
            )
        },
        popEnterTransition = {
            scaleIn(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION),
                initialScale = .95f,
            ) + fadeIn(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
            )
        },
        popExitTransition = {
            scaleOut(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION),
                targetScale = .95f,
            ) + fadeOut(
                animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
            )
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
                context.redirectToTMDB()
            },
            onContinueWithoutAccountClick = {
                navController.navigateToMainScreen()
            },
            onLoginClick = {
                navController.navigateToSignIn()
            }
        )
        signInScreen(
            onBackClick = {
                navController.popBack()
            },
            onNavigateToMainScreen = {
                navController.navigateToMainScreen()
            }
        )
        mainScreen(
            onMovieItemClick = { movieId ->
                navController.navigateToMovieDetailsScreen(movieId)
            },
            onSeeAllMoviesClick = { mediaType ->
                navController.navigateToAllMoviesScreen(mediaType)
            },
            onPersonClick = { personId ->
                navController.navigateToPersonDetailsScreen(personId)
            },
            onTvShowClick = { tvShowId ->
                navController.navigateToTvShowsDetailsScreen(tvShowId)
            },
            onSeeAllTvShowsClick = { allTvShowsScreenParam ->
                navController.navigateToAllTvShowsScreen(allTvShowsScreenParam)
            },
            onSeeAllPeopleClick = { allPeopleScreenParam ->
                navController.navigateToAllPeopleScreen(allPeopleScreenParam)
            }
        )
        movieDetailsScreen(
            onBackClick = {
                navController.popBack()
            },
            onImageClick = { imagePath ->
                navController.navigateToImageViewerScreen(imagePath)
            },
            onMovieClick = { movieId ->
                navController.navigateToMovieDetailsScreen(movieId)
            },
            onPersonClick = { personId ->
                navController.navigateToPersonDetailsScreen(personId)
            },
            onSeeAllSimilarClick = { mediaType ->
                navController.navigateToAllMoviesScreen(mediaType)
            }
        )
        tvShowDetailsScreen(
            onBackClick = {
                navController.popBack()
            },
            onImageClick = { imagePath ->
                navController.navigateToImageViewerScreen(imagePath)
            },
            onTvShowClick = { seriesId ->
                navController.navigateToTvShowsDetailsScreen(seriesId)
            },
            onPersonClick = { personId ->
                navController.navigateToPersonDetailsScreen(personId)
            },
            onSeeAllTvShowsClick = { allTvShowsParam ->
                navController.navigateToAllTvShowsScreen(allTvShowsParam)
            }
        )
        personDetailsScreen(
            onBackClick = {
                navController.popBack()
            },
            onImageClick = { imagePath ->
                navController.navigateToImageViewerScreen(imagePath)
            },
            onMovieItemClick = { movieId ->
                navController.navigateToMovieDetailsScreen(movieId)
            },
            onTvShowItemClick = { seriesId ->
                navController.navigateToTvShowsDetailsScreen(seriesId)
            }
        )
        allMoviesScreen(
            onMovieItemClick = { movieId ->
                navController.navigateToMovieDetailsScreen(movieId)
            },
            onBackClick = {
                navController.popBack()
            }
        )
        allTvShowsScreen(
            onBackClick = { navController.popBack() },
            onTvShowClick = { seriesId ->
                navController.navigateToTvShowsDetailsScreen(seriesId)
            }
        )
        allPeopleScreen(
            onPersonClick = { personId ->
                navController.navigateToPersonDetailsScreen(personId)
            },
            onBackClick = {
                navController.popBack()
            }
        )
        imageViewerScreen(
            onBackClick = { navController.popBack() }
        )
    }
}

val NavHostController.canPopBack: Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

fun NavHostController.popBack() {
    if (canPopBack) {
        popBackStack()
    }
}

private fun Context.redirectToTMDB() {
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(TMDB_SIGN_IN_PAGE)
    }
    startActivity(intent)
}
