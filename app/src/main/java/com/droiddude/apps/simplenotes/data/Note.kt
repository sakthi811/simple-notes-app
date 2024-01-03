package com.droiddude.apps.simplenotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String ,
    val message : String ,
    val updatedTime : Long
)
