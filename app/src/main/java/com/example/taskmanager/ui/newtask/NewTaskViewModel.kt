package com.example.taskmanager.ui.newtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dao.TaskDao
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(private val repository: TaskRepository): ViewModel() {

    private val _currentTask: MutableLiveData<Task> = MutableLiveData<Task>()
    val currentTask
        get() = _currentTask

    fun getTaskWithId(id: Int) = viewModelScope.launch {
        _currentTask.value = repository.getTaskWithId(id)
    }

    fun addTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(task)
    }


    fun updateTask(task : Task) = viewModelScope.launch(Dispatchers.IO){
        repository.updateTask(task)
    }
}