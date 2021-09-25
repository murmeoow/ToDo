package com.example.taskmanager.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.dao.TaskDao
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): ViewModel() {

    private val repository : TaskRepository

    val allTasks: LiveData<List<Task>>
//    val _allTasks : MutableLiveData<List<Task>>
//    get() = allTasks.value

    init {
        val dao =TaskDatabase.getInstance(application).taskDao
        repository = TaskRepository(dao)
        allTasks = repository.allTasks
    }


}