package uz.john.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = CineManiaColors.Purple.primary,
    onPrimary = CineManiaColors.White,
    primaryContainer = CineManiaColors.Purple.light,
    onPrimaryContainer = CineManiaColors.White,
    inversePrimary = CineManiaColors.Purple.dark,
    secondary = CineManiaColors.Green.primary,
    onSecondary = CineManiaColors.White,
    secondaryContainer = CineManiaColors.Green.light,
    onSecondaryContainer = CineManiaColors.White,
    tertiary = CineManiaColors.Green.primary,
    onTertiary = CineManiaColors.White,
    tertiaryContainer = CineManiaColors.Green.light,
    onTertiaryContainer = CineManiaColors.White,

    error = CineManiaColors.Red.primary,
    onError = CineManiaColors.White,
    errorContainer = CineManiaColors.Red.light,
    onErrorContainer = CineManiaColors.White,

    background = CineManiaColors.Black,
    onBackground = CineManiaColors.White,
    surface = CineManiaColors.Black,
    onSurface = CineManiaColors.White,
    surfaceVariant = CineManiaColors.ExtraDarkGray,
    onSurfaceVariant = CineManiaColors.White,
    surfaceTint = CineManiaColors.White,
    inverseSurface = CineManiaColors.LightGray,
    inverseOnSurface = CineManiaColors.Black,

    outline = CineManiaColors.Gray,
    outlineVariant = CineManiaColors.LightGray,
    scrim = CineManiaColors.ExtraLightGray.copy(alpha = 0.8f)
)

private val LightColorScheme = lightColorScheme(
    primary = CineManiaColors.Purple.primary,
    onPrimary = CineManiaColors.White,
    primaryContainer = CineManiaColors.Purple.light,
    onPrimaryContainer = CineManiaColors.White,
    inversePrimary = CineManiaColors.Purple.dark,
    secondary = CineManiaColors.Green.primary,
    onSecondary = CineManiaColors.White,
    secondaryContainer = CineManiaColors.Green.light,
    onSecondaryContainer = CineManiaColors.White,
    tertiary = CineManiaColors.Green.primary,
    onTertiary = CineManiaColors.White,
    tertiaryContainer = CineManiaColors.Green.light,
    onTertiaryContainer = CineManiaColors.White,

    error = CineManiaColors.Red.primary,
    onError = CineManiaColors.White,
    errorContainer = CineManiaColors.Red.light,
    onErrorContainer = CineManiaColors.White,

    background = CineManiaColors.White,
    onBackground = CineManiaColors.Black,
    surface = CineManiaColors.White,
    onSurface = CineManiaColors.Black,
    surfaceVariant = CineManiaColors.ExtraLightGray,
    onSurfaceVariant = CineManiaColors.Black,
    surfaceTint = CineManiaColors.Black,
    inverseSurface = CineManiaColors.DarkGray,
    inverseOnSurface = CineManiaColors.White,

    outline = CineManiaColors.Gray,
    outlineVariant = CineManiaColors.DarkGray,
    scrim = CineManiaColors.ExtraDarkGray.copy(alpha = 0.8f)
)

@Composable
fun CineManiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}