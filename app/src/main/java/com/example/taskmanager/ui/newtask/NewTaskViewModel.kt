package com.example.taskmanager.ui.newtask

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

class NewTaskViewModel(
    private val taskId: Int,
    application: Application) : ViewModel() {

    private val repository : TaskRepository
    val currentTask : LiveData<Task>

    init{
        val dao = TaskDatabase.getInstance(application).taskDao
        repository = TaskRepository(dao)
        currentTask = repository.getTaskWithId(taskId)
    }

    fun addTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(task)
    }


    fun updateTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.updateTask(task)
    }


}