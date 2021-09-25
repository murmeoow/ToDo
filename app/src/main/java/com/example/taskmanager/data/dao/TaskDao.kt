package com.example.taskmanager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskmanager.data.entity.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task : Task)

    @Delete
    suspend fun deleteTask(task : Task)

    @Query("SELECT * FROM tasks ORDER BY taskDate")
    fun getAllTasks() : LiveData<List<Task>>
}