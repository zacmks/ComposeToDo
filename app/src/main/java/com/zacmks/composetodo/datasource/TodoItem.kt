package com.zacmks.composetodo.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val text: String,
    var checked: Boolean = false
)