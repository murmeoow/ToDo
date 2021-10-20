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

    suspend fun updateTask(task: Task){
        tasksDao.updateTask(task)
    }

    fun getTaskWithId(taskId: Int): LiveData<Task> {
        return tasksDao.getTaskWithId(taskId)
    }

}