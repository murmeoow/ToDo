package com.example.taskmanager.ui.newtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.databinding.FragmentNewTaskBinding
import com.example.taskmanager.ui.tasklist.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewTaskFragment() : BottomSheetDialogFragment() {

    private var dueDate : Date? = null
    private var currentTask : Task? = null
    private val newTaskViewModel: NewTaskViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private lateinit var binding: FragmentNewTaskBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        binding = FragmentNewTaskBinding.inflate(inflater, container, false )

        getPickedTask()

        binding.ibCalendar.setOnClickListener{
            setDate()
        }

        binding.btnAddTask.setOnClickListener {
            if (currentTask!=null) {
                newTaskViewModel.updateTask(
                    Task(currentTask?._id, binding.etTaskName.text.toString(), calendar.time,
                    dueDate, false))
            }else {
                newTaskViewModel.addTask(Task(
                    null, binding.etTaskName.text.toString(), calendar.time,
                    dueDate, false))
            }
            findNavController().popBackStack()

        }

        return binding.root
    }

    private fun setDate(){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()
        datePicker.show(parentFragmentManager, "")
        datePicker.addOnPositiveButtonClickListener {
            dueDate= Date(it)
        }
    }

    private fun getPickedTask(){
        val args = NewTaskFragmentArgs.fromBundle(requireArguments())
        if (args.taskId != -1) {
            newTaskViewModel.getTaskWithId(args.taskId)
            newTaskViewModel.currentTask.observe(viewLifecycleOwner,{
                currentTask = it
                binding.etTaskName.setText(currentTask?.taskName)
                dueDate = currentTask?.taskDueDate
            })

        }
    }
}