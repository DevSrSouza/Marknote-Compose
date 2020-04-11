package br.com.devsrsouza.marknote.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,

    var source: String,
    var noteColor: NoteColor? = null,
    val createTimeTimestamp: Long = System.currentTimeMillis()
)

enum class NoteColor(
    val color: Long
) {
    Red(0xFFe57373),
    Green(0xFF81c784),
    Blue(0xFF64b5f6),
    Orange(0xFFff8a65),
    Purple(0xFF9575cd)
}