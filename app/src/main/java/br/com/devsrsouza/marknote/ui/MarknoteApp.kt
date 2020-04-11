package br.com.devsrsouza.marknote.ui

import androidx.compose.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import br.com.devsrsouza.marknote.model.MarknoteTheme
import br.com.devsrsouza.marknote.model.MarknoteThemeAmbient
import br.com.devsrsouza.marknote.repository.Repository

val RepositoryThemeAmbient: ProvidableAmbient<Repository> = ambientOf<Repository>()
@Composable
val Repository.Companion.current: Repository
    get() = RepositoryThemeAmbient.current

@Composable
fun MarknoteApp(
    repository: Repository
) {
    val theme = remember { MarknoteTheme() }

    MaterialTheme(
        colors = theme.colorPalette
    ) {
        Providers(
            MarknoteThemeAmbient provides theme,
            RepositoryThemeAmbient provides repository
        ) {
            AppContent()
        }
    }
}

@Composable
private fun AppContent() {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        MainScreen()
    }
}