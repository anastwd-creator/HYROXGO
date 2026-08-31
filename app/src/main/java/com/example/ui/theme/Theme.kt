package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val HyroxColorScheme = darkColorScheme(
    primary = NeonYellow,
    onPrimary = Charcoal900,
    primaryContainer = NeonYellowContainer,
    onPrimaryContainer = NeonYellow,
    secondary = TextPrimary,
    onSecondary = Charcoal900,
    secondaryContainer = Charcoal700,
    onSecondaryContainer = TextPrimary,
    tertiary = NeonYellowDim,
    onTertiary = Charcoal900,
    background = Charcoal900,
    onBackground = TextPrimary,
    surface = Charcoal800,
    onSurface = TextPrimary,
    surfaceVariant = Charcoal700,
    onSurfaceVariant = TextSecondary,
    outline = Charcoal600,
    outlineVariant = Charcoal700,
    error = ErrorRed,
    onError = TextPrimary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // HYROX aesthetic is dedicated high-contrast dark mode
    dynamicColor: Boolean = false, // Keep high-contrast intentional neon palette
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = HyroxColorScheme,
        typography = Typography,
        content = content
    )
}

