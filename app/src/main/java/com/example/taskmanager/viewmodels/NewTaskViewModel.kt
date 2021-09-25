package com.example.taskmanager.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewTaskViewModel(application: Application) : ViewModel() {

    private val repository : TaskRepository

    init{
        val dao = TaskDatabase.getInstance(application).taskDao
        repository = TaskRepository(dao)
    }

    fun addTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(task)
    }

    fun deleteTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteTask(task)
    }

}