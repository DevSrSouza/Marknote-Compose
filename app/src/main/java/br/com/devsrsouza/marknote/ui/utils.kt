package br.com.devsrsouza.marknote.ui

import androidx.compose.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import br.com.devsrsouza.marknote.repository.room.NoteColor

@Composable
fun <T> observer(data: LiveData<T>): T? {
    var result = state<T?> { data.value }
    val observer = remember { Observer<T> { result.value = it } }

    onCommit(data) {
        data.observeForever(observer)
        onDispose { data.removeObserver(observer) }
    }
    return result.value
}

@Composable
fun NoteColor?.colorOrDefault(): Color {
    return this?.color?.let { Color(it) }
        ?: MaterialTheme.colors.background
}