package com.droiddude.apps.simplenotes.model

import com.droiddude.apps.simplenotes.data.Note


sealed interface NoteEvent {

    data class SetTitle(val title : String) : NoteEvent

    data class SetMessage(val message : String) : NoteEvent

    data class SetUpdateTime(val currentTime : Long) : NoteEvent

    data class SortNote(val sortType : SortType) : NoteEvent

    data class DeleteNote(val note : Note) : NoteEvent

    object ShowDialog : NoteEvent

    object HideDialog : NoteEvent

    object SaveNote : NoteEvent
}