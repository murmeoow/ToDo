package com.example.taskmanager.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.repository.TaskRepository

class TaskViewModel(application: Application): ViewModel() {

    private val repository : TaskRepository

    val allTasks: LiveData<List<Task>>
//    val _allTasks : MutableLiveData<List<Task>>
//    get() = allTasks

    init {
        val dao =TaskDatabase.getInstance(application).taskDao
        repository = TaskRepository(dao)
        allTasks = repository.allTasks
    }


}