package com.zacmks.composetodo.datasource

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM TodoItem")
    fun getAll(): LiveData<List<TodoItem>>

    @Query("SELECT * FROM TodoItem WHERE checked = '1'")
    fun checkedItems(): LiveData<List<TodoItem>>?

    @Query("DELETE FROM TodoItem WHERE checked = '1'")
    suspend fun deleteSelected()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TodoItem)

    @Update
    suspend fun update(item: TodoItem)
}