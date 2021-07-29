package com.zacmks.composetodo

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ToDoViewModel : ViewModel() {

    val toDoItems = mutableStateListOf<ToDoItem>()

    val selectedItems = mutableStateListOf<ToDoItem>()

    fun addItem(item: ToDoItem) {
        toDoItems.add(item)
    }

    fun toggleItem(item: ToDoItem) {
        val foundItem = selectedItems.find { it == item }
        if (foundItem != null) {
            selectedItems.remove(foundItem)
        } else {
            selectedItems.add(item)
        }
    }

    fun deleteSelected() {
        toDoItems.removeAll(selectedItems)
        selectedItems.clear()
    }

}