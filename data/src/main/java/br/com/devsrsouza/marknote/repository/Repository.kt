package br.com.devsrsouza.marknote.repository

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.coroutineScope
import br.com.devsrsouza.marknote.repository.room.NoteEntity
import br.com.devsrsouza.marknote.repository.room.NoteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(
    private val context: Context,
    private val appLifecycle: Lifecycle
) {
    companion object {}

    private val database = NoteRoomDatabase.getDatabase(context)
    private val dao = database.noteDao()

    fun getNotesSortedByCreationTime(): LiveData<List<NoteEntity>> {
        return dao.getNotesSortedByCreationTime()
    }

    fun insert(note: NoteEntity) {
        val copy = note.copy()
        appLifecycle.coroutineScope.launch(Dispatchers.IO) {
            dao.insert(copy)
        }
    }

    fun update(note: NoteEntity) {
        val copy = note.copy()
        appLifecycle.coroutineScope.launch(Dispatchers.IO) {
            dao.update(copy)
        }
    }

    fun delete(note: NoteEntity) {
        appLifecycle.coroutineScope.launch(Dispatchers.IO) {
            dao.delete(note)
        }
    }

    fun clearAll() {
        appLifecycle.coroutineScope.launch(Dispatchers.IO) {
            dao.clearAll()
        }
    }

}