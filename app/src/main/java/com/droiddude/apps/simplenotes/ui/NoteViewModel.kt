package com.droiddude.apps.simplenotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddude.apps.simplenotes.data.Note
import com.droiddude.apps.simplenotes.data.NoteDao
import com.droiddude.apps.simplenotes.model.NoteEvent
import com.droiddude.apps.simplenotes.model.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao : NoteDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TIME)
    private val _state = MutableStateFlow(NoteState())
    private val _notes = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.TITLE -> dao.getNotesByTitle()
                SortType.MESSAGE -> dao.getNotesByMessage()
                SortType.TIME -> dao.getNotesByLatestUpdated()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val state = combine(_state, _notes, _sortType) { state, notes, sortType ->
        state.copy(
            notes = notes,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event : NoteEvent) {
        when(event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NoteEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingNote = false
                )}
            }
            NoteEvent.SaveNote -> {
                val title = state.value.title
                val message = state.value.message
                val currentTime = System.currentTimeMillis()
                if(title.isBlank() || message.isBlank()) return;
                val note = Note(title = title, message = message, updatedTime = currentTime)
                viewModelScope.launch {
                    dao.upsertNote(note)
                }
                _state.update { it.copy(
                    isAddingNote = false,
                    title = "",
                    message = "",
                    updatedTime = 0L
                )}
            }
            is NoteEvent.SetMessage -> {
                _state.update { it.copy(
                    message = event.message
                )}
            }
            is NoteEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                )}
            }
            is NoteEvent.SetUpdateTime -> {
                _state.update { it.copy(
                    updatedTime = System.currentTimeMillis()
                )}
            }
            NoteEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingNote = true
                )}
            }
            is NoteEvent.SortNote -> {
                _sortType.value = event.sortType
            }
        }
    }
}