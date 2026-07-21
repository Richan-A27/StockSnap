package com.stocksnap.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    secondary = AccentGreen,
    onSecondary = Color.White,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = Color.Transparent, // Surfaces are typically glass in this theme
    onSurface = TextPrimary,
    error = StatusDanger,
    onError = Color.White
)

@Composable
fun StockSnapTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = StockSnapTypography,
        content = content
    )
}
