package uz.john.cinemania.image_viewer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val IMAGE_PATH_ARG = "IMAGE_PATH_ARG"
const val IMAGE_VIEWER = "IMAGE_VIEWER"
const val IMAGE_VIEWER_ROUTE = "$IMAGE_VIEWER/{$IMAGE_PATH_ARG}"

fun NavController.navigateToImageViewerScreen(imagePath: String, navOptions: NavOptions? = null) {
    navigate(
        route = "$IMAGE_VIEWER$imagePath",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.imageViewerScreen(
    onBackClick: () -> Unit
) {
    composable(IMAGE_VIEWER_ROUTE) {
        val imagePath = it.arguments?.getString(IMAGE_PATH_ARG)
        imagePath?.let {
            ImageViewerScreen(
                imagePath = imagePath,
                onBackClick = onBackClick
            )
        }
    }
}