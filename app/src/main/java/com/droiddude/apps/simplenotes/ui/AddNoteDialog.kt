package com.droiddude.apps.simplenotes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddude.apps.simplenotes.model.NoteEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialog(
    state : NoteState,
    onEvent : (NoteEvent) -> Unit,
    modifier : Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(NoteEvent.HideDialog)
        },
        title = { Text(text = "Add Note") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(NoteEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Add title")
                    }
                )
                TextField(
                    value = state.message,
                    onValueChange = {
                        onEvent(NoteEvent.SetMessage(it))
                    },
                    placeholder = {
                        Text(text = "Add Message")
                    }
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd) {
                Button(onClick = { onEvent(NoteEvent.SaveNote) }) {
                    Text(text = "Save")
                }
            }
        })
}