package br.com.devsrsouza.marknote.repository.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY createTimeTimestamp ASC")
    fun getNotesSortedByCreationTime(): LiveData<List<NoteEntity>>

    @Insert
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("DELETE FROM notes")
    suspend fun clearAll()
}