package com.zacmks.composetodo

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ToDoViewModel : ViewModel() {

    var toDoItems = mutableStateListOf<ToDoItem>()
        private set

    var selectedItems = mutableStateListOf<ToDoItem>()
        private set

    fun addItem(item: ToDoItem) {
        toDoItems.add(item)
    }

    fun selectItem(item: ToDoItem) {
        selectedItems.add(item)
    }

    fun removeItems(items: List<ToDoItem>) {
        toDoItems.removeAll(items)
    }

}