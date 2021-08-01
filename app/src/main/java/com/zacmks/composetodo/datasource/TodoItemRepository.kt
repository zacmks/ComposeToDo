package com.zacmks.composetodo.datasource

import androidx.lifecycle.LiveData

class TodoItemRepository(private val dao: TodoItemDao) {

    val getAllItems: LiveData<List<TodoItem>> = dao.getAll()

    val checkedItems: LiveData<List<TodoItem>>? = dao.checkedItems()

    suspend fun deleteSelected() = dao.deleteSelected()

    suspend fun insert(item: TodoItem) = dao.insert(item)

    suspend fun update(item: TodoItem) = dao.update(item)
}