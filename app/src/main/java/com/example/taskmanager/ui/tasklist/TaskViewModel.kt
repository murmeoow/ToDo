package com.example.taskmanager.ui.tasklist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.repository.TaskRepository
import kotlinx.coroutines.launch

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

    private val _navigateToUpdateTask = MutableLiveData<Int>()
    val navigateToUpdateTask
        get() = _navigateToUpdateTask

    fun onTaskClicked(id : Int){
        _navigateToUpdateTask.value = id
    }

    fun onTaskUpdateNavigated(){
        _navigateToUpdateTask.value = null
    }

    fun onTaskChecked(task: Task, isChecked: Boolean) = viewModelScope.launch {
        repository.updateTask(task.copy(status = isChecked))
    }

    fun onTaskSwiped(task : Task ) = viewModelScope.launch {
        repository.deleteTask(task)

    }
}