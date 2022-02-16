package com.example.taskmanager.ui.tasklist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository): ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.allTasks
    private val _currentTask: MutableLiveData<Task> = MutableLiveData<Task>()
    val currentTask
        get() = _currentTask

    private val _navigateToUpdateTask = MutableLiveData<Int>()
    val navigateToUpdateTask
        get() = _navigateToUpdateTask

    fun onTaskClicked(id : Int){
        _navigateToUpdateTask.value = id
    }

    fun onTaskUpdateNavigated(){
        _navigateToUpdateTask.value = null
    }

    fun onTaskChecked(task: Task, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTask(task.copy(status = isChecked))
    }

    fun onTaskDelete(task : Task ) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTask(task)

    }

    fun addTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(task)
    }


    fun updateTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.updateTask(task)
    }

    fun getTaskWithId(id: Int) = viewModelScope.launch {
        _currentTask.value = repository.getTaskWithId(id)
    }
}