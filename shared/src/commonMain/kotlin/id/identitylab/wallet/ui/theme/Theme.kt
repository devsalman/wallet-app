package id.identitylab.wallet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = SurfaceWhite,
    primaryContainer = LightBlue100,
    onPrimaryContainer = TextPrimary,
    secondary = Orange500,
    onSecondary = SurfaceWhite,
    secondaryContainer = WarnBg,
    onSecondaryContainer = TextPrimary,
    background = Canvas,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = LightBlue50,
    onSurfaceVariant = TextMuted,
    outline = BorderSubtle,
    outlineVariant = BorderLight,
    error = DangerText,
    onError = SurfaceWhite,
    errorContainer = DangerBg,
    onErrorContainer = DangerText,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkTint,
    onPrimary = DarkBackground,
    secondary = DarkTint,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkBackground,
    onSurface = DarkText,
)

@Composable
fun NufidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = NufidTypography,
        shapes = NufidShapes,
        content = content,
    )
}
