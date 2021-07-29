package com.zacmks.composetodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue700Light,
    primaryVariant = Blue700Dark,
    secondary = Pink400
)

private val LightColorPalette = lightColors(
    primary = Blue700,
    primaryVariant = Blue700Dark,
    secondary = Pink400
)

private val LightColorPaletteSecondVariant = LightColorPalette.copy(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val DarkColorPaletteSecondVariant = DarkColorPalette.copy(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun ComposeToDoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    secondVariant: Boolean = false,
    content: @Composable() () -> Unit,
) {
    val colors: Colors = if (secondVariant) {
        if (darkTheme) DarkColorPaletteSecondVariant else LightColorPaletteSecondVariant
    } else {
        if (darkTheme) DarkColorPalette else LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}