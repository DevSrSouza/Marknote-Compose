package br.com.devsrsouza.marknote.ui

import androidx.compose.Composable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.material.icons.filled.Brightness3
import androidx.ui.material.icons.filled.Brightness5
import br.com.devsrsouza.marknote.model.MarknoteTheme
import br.com.devsrsouza.marknote.repository.Repository
import br.com.devsrsouza.marknote.repository.room.NoteEntity
import br.com.devsrsouza.marknote.ui.note.NoteList

@Composable
fun MainScreen() {
    Scaffold(
        topAppBar = { AppBar() },
        floatingActionButton = { AddNoteButton() }
    ) {
        NoteList(
            Repository.current.getNotesSortedByCreationTime()
        )
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        title = { Text("Marknote") },
        actions = {
            SwitchThemeButton()
        },
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun AddNoteButton() {
    val repository = Repository.current
    FloatingActionButton(
        onClick = {
            repository.insert(
                NoteEntity(
                    source = "edit the text"
                )
            )
        }
    ) {
        Icon(
            Icons.Filled.Add,
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
private fun SwitchThemeButton() {
    val theme = MarknoteTheme.current
    IconButton(
        onClick = theme::switchTheme
    ) {
        if(theme.isLight)
            Icon(Icons.Filled.Brightness3)
        else
            Icon(Icons.Filled.Brightness5)
        // FIXME: Remove the code above and use this when got fixed
        // https://issuetracker.google.com/issues/152712588
        /*Icon(
            if (theme.isLight)
                Icons.Filled.Brightness3
            else
                Icons.Filled.Brightness5
        )*/
    }
}