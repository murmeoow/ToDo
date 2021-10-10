package com.example.taskmanager.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.databinding.FragmentNewTaskBinding
import com.example.taskmanager.viewmodels.NewTaskViewModel
import com.example.taskmanager.viewmodels.TaskViewModelFactory
import java.util.*


class NewTaskFragment() : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val binding = FragmentNewTaskBinding.inflate(inflater, container, false )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = TaskViewModelFactory( application)

        val newTaskViewModel = ViewModelProvider(this, viewModelFactory).get(NewTaskViewModel::class.java)

        binding.btnAddTask.setOnClickListener {
            var calendar = Calendar.getInstance()
                //.get(Calendar.YEAR + Calendar.MONTH+Calendar.DAY_OF_MONTH)
            val newTask = Task(null, binding.etTaskName.text.toString(),binding.etTaskDecsription.text.toString(),
                                        calendar.time,false)
            newTaskViewModel.addTask(newTask)
            findNavController().navigate(NewTaskFragmentDirections.actionNewTaskFragmentToTasksListFragment())
        }

        return binding.root
    }


}