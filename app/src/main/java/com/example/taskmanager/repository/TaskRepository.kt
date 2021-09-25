package com.example.taskmanager.repository

import androidx.lifecycle.LiveData
import com.example.taskmanager.data.dao.TaskDao
import com.example.taskmanager.data.entity.Task

class TaskRepository(private val tasksDao: TaskDao) {

    val allTasks : LiveData<List<Task>> = tasksDao.getAllTasks()

    suspend fun insertTask(task : Task){
        tasksDao.insertTask(task)
    }

    suspend fun deleteTask(task: Task){
        tasksDao.deleteTask(task)
    }
}