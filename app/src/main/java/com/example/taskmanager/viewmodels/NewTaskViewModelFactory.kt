package com.example.taskmanager.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewTaskViewModelFactory(private val taskId : Int, private val application : Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NewTaskViewModel::class.java)) {
            return NewTaskViewModel(taskId,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}