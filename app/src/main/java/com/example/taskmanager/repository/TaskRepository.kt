package com.example.taskmanager.repository

import androidx.lifecycle.LiveData
import com.example.taskmanager.data.dao.TaskDao
import com.example.taskmanager.data.entity.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val tasksDao: TaskDao) {

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

    suspend fun getTaskWithId(taskId: Int): Task {
        return tasksDao.getTaskWithId(taskId)
    }

}