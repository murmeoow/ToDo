package com.example.taskmanager.ui.tasklist

import android.app.Application
import androidx.lifecycle.*
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

    fun searchDatabase(searchQuery: String): LiveData<List<Task>>{
        return repository.searchDatabase(searchQuery).asLiveData()
    }




}