package com.droiddude.apps.simplenotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getNotesByTitle() : Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY message ASC")
    fun getNotesByMessage() : Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY updatedTime DESC")
    fun getNotesByLatestUpdated() : Flow<List<Note>>
}