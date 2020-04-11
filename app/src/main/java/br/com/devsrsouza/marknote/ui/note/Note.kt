package br.com.devsrsouza.marknote.ui.note

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.lifecycle.LiveData
import androidx.ui.core.Modifier
import androidx.ui.core.drawShadow
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Edit
import androidx.ui.material.icons.filled.Fullscreen
import androidx.ui.material.icons.filled.InvertColors
import androidx.ui.material.icons.filled.InvertColorsOff
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import br.com.devsrsouza.marknote.repository.Repository
import br.com.devsrsouza.marknote.repository.room.NoteColor
import br.com.devsrsouza.marknote.repository.room.NoteEntity
import br.com.devsrsouza.marknote.ui.colorOrDefault
import br.com.devsrsouza.marknote.ui.current
import br.com.devsrsouza.marknote.ui.observer

@Composable
fun NoteList(notesLiveData: LiveData<List<NoteEntity>>) {
    val notes = observer(data = notesLiveData)

    if (notes != null) {
        AdapterList(data = notes) {
            NoteCard(it)
        }
    } else {
        Text("Loading.........")
    }
}

@Composable
fun NoteCard(note: NoteEntity) {
    Card(
        modifier = Modifier.padding(16.dp),
        color = note.noteColor.colorOrDefault(),
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Note(note)
    }
}

@Composable
fun Note(note: NoteEntity) {
    val repository = Repository.current

    val editState = state { false }
    val sourceState = state { note.source }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            NoteToolBar(
                note = note,
                editState = editState,
                onSaveNote = { note.updateSource(sourceState.value, repository) }
            )

            NoteContent(
                note = note,
                editState = editState,
                sourceState = sourceState
            )
        }
    }
}

@Composable
private fun NoteToolBar(
    note: NoteEntity,
    editState: MutableState<Boolean>,
    onSaveNote: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        arrangement = Arrangement.SpaceBetween
    ) {
        FullscreenButton(note)
        EditButton(note, editState, onSaveNote)
    }
}

@Composable
private fun FullscreenButton(note: NoteEntity) {
    IconButton(onClick = {}) {
        Icon(Icons.Filled.Fullscreen)
    }
}

private val iconSize = Modifier.preferredSize(32.dp)

@Composable
private fun EditButton(
    note: NoteEntity,
    editState: MutableState<Boolean>,
    onSaveNote: () -> Unit
) {
    FlowRow(
        crossAxisAlignment = FlowCrossAxisAlignment.Center
    ) {
        val repository = Repository.current

        if(editState.value) {
            Row(
                modifier = Modifier.drawShadow(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 6.dp
                ).drawBackground(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(16.dp)
                )
            ) {
                SmallIconButton(
                    onClick = { note.updateNoteColor(null, repository) }
                ) {
                    Icon(
                        Icons.Filled.InvertColorsOff,
                        modifier = iconSize
                    )
                }

                for (noteColor in NoteColor.values()) {
                    SmallIconButton(
                        onClick = { note.updateNoteColor(noteColor, repository) }
                    ) {
                        Icon(
                            Icons.Filled.InvertColors,
                            modifier = iconSize,
                            tint = Color(noteColor.color)
                        )
                    }

                }
            }
        }

        IconButton(onClick = {
            editState.value = !editState.value

            if (!editState.value) {
                onSaveNote()
            }
        }) {
            Icon(
                Icons.Filled.Edit,
                tint = if (!editState.value) contentColor() else Color.Blue
            )
        }

    }
}

@Composable
private fun NoteContent(
    note: NoteEntity,
    editState: MutableState<Boolean>,
    sourceState: MutableState<String>
) {
    val (editedSource, setEditedSource) = sourceState

    Box(
        paddingStart = 8.dp,
        paddingEnd = 8.dp,
        paddingBottom = 8.dp
    ) {
        ProvideTextStyle(
            TextStyle(
                //color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp
            )
        ) {
            if (!editState.value)
                NoteMarkdownContent(note)
            else
                NoteEditContent(
                    content = editedSource,
                    onValueChanged = setEditedSource
                )
        }
    }
}

@Composable
private fun NoteMarkdownContent(note: NoteEntity) {
    // TODO: Use a Markdown render composable in the future.
    Text(
        note.source,
        style = currentTextStyle().merge(TextStyle(color = MaterialTheme.colors.onBackground))
    )
}

@Composable
private fun NoteEditContent(content: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = content,
        onValueChange = onValueChanged,
        textStyle = currentTextStyle().merge(TextStyle(color = MaterialTheme.colors.onBackground))
    )
}

@Composable
private fun SmallIconButton(
    onClick: () -> Unit,
    child: @Composable() () -> Unit
) {
    Clickable(
        modifier = Modifier.ripple(
            radius = 16.dp
        ),
        onClick = onClick,
        children = child
    )
}

private fun NoteEntity.updateNoteColor(
    color: NoteColor?,
    repository: Repository
) {
    if(color != noteColor) {
        noteColor = color

        repository.update(this)
    }
}

private fun NoteEntity.updateSource(
    editedSource: String,
    repository: Repository
) {
    if(source != editedSource) {
        source = editedSource

        repository.update(this)
    }
}
