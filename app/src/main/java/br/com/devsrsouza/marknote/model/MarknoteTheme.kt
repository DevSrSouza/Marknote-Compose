package br.com.devsrsouza.marknote.model

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.ambientOf
import androidx.ui.graphics.Color
import androidx.ui.material.ColorPalette
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette
import br.com.devsrsouza.marknote.constants.primaryColor

val MarknoteThemeAmbient = ambientOf<MarknoteTheme>()

@Model
class MarknoteTheme {
    companion object {

        @Composable
        val current: MarknoteTheme
            get() = MarknoteThemeAmbient.current
    }

    var colorPalette: ColorPalette = lightThemeColors

    val isLight: Boolean
        get() = colorPalette.isLight

    fun switchTheme() {
        colorPalette = if(isLight)
            darkThemeColors
        else lightThemeColors
    }
}

private val lightThemeColors = lightColorPalette(
    primary = primaryColor,
    primaryVariant = primaryColor,
    onPrimary = Color.White,
    onSurface = Color.Black
)

private val darkThemeColors = darkColorPalette(
    primary = primaryColor,
    primaryVariant = primaryColor,
    onPrimary = Color.White,
    onSurface = Color.White
)
