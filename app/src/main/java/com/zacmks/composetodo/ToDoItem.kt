package com.zacmks.composetodo

import java.util.*

data class ToDoItem(
    val text: String,
//    val isDone: Boolean,
    val id: UUID = UUID.randomUUID(),
)