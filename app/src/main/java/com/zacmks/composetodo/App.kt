package com.zacmks.composetodo

import android.app.Application
import com.zacmks.composetodo.datasource.AppDatabase
import com.zacmks.composetodo.datasource.TodoItemRepository

/**
 * [App] is called when the application starts.
 */
class App : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TodoItemRepository(database.TodoItemDao()) }
}