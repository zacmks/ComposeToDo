package com.zacmks.composetodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zacmks.composetodo.datasource.TodoItem
import com.zacmks.composetodo.datasource.TodoItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoItemViewModel(private val itemRepository: TodoItemRepository) : ViewModel() {

    val todoItems: LiveData<List<TodoItem>> = itemRepository.getAllItems
    val isItemChecked: LiveData<List<TodoItem>>? = itemRepository.checkedItems

    fun addItem(newItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.insert(newItem)
        }
    }

    fun toggleItem(item: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.update(item)
        }
    }

    fun deleteSelected() {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.deleteSelected()
        }
    }
}

class TodoItemViewModelFactory(private val repository: TodoItemRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}