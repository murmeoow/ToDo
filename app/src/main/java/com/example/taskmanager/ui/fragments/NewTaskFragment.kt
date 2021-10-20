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
import com.example.taskmanager.viewmodels.NewTaskViewModelFactory
import com.example.taskmanager.viewmodels.TaskViewModelFactory
import java.util.*


class NewTaskFragment() : Fragment() {

    private lateinit var dueDate : Date
    private lateinit var currentTask : Task

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val binding = FragmentNewTaskBinding.inflate(inflater, container, false )

        val application = requireNotNull(this.activity).application

        val args = NewTaskFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = NewTaskViewModelFactory(args.taskId, application)

        val newTaskViewModel = ViewModelProvider(this, viewModelFactory).get(NewTaskViewModel::class.java)

        val calendarView = binding.calendarView
        val calendar = Calendar.getInstance()

        if (args.taskId.equals(-1) == false) {

            newTaskViewModel.currentTask.observe(viewLifecycleOwner, {

                currentTask =  it
                binding.etTaskName.setText(currentTask.taskName)

                dueDate = currentTask.taskDueDate
                calendar.setTime(dueDate)
                val milliTime = calendar.timeInMillis
                calendarView.setDate(milliTime, true,true)
            })

        }

        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            calendar.clear()
            calendar.set(year, month, dayOfMonth)
            dueDate = calendar.time
        }


        binding.btnAddTask.setOnClickListener {


            if (args.taskId.equals(-1) == false){
                val editedTask = Task(
                    currentTask._id, binding.etTaskName.text.toString(), calendar.time,
                    dueDate, false
                )
                newTaskViewModel.updateTask(editedTask)
            }else {
                val newTask = Task(
                    null, binding.etTaskName.text.toString(), calendar.time,
                    dueDate, false
                )
                newTaskViewModel.addTask(newTask)
            }
            findNavController().navigate(NewTaskFragmentDirections.actionNewTaskFragmentToTasksListFragment())
        }

        return binding.root
    }


}