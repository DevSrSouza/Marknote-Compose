package br.com.devsrsouza.marknote.repository.room

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toNoteColor(value: String?) = value?.let { enumValueOf<NoteColor>(it) }

    @TypeConverter
    fun fromNoteColor(value: NoteColor?) = value?.name
}