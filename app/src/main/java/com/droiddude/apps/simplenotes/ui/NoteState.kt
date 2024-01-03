package com.droiddude.apps.simplenotes.ui

import com.droiddude.apps.simplenotes.data.Note
import com.droiddude.apps.simplenotes.model.SortType

data class NoteState(
    val notes : List<Note> = emptyList(),
    val title : String = "",
    val message : String = "",
    val updatedTime : Long = 0L,
    val isAddingNote : Boolean = false,
    val sortType : SortType = SortType.TIME
)
