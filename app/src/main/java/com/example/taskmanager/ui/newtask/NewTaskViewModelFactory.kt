package com.example.taskmanager.ui.newtask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NewTaskViewModelFactory(
    private  val taskId: Int,
    private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTaskViewModel::class.java)){
            return NewTaskViewModel(
                taskId,
                application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
