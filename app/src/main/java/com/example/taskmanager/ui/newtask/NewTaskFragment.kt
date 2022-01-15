package com.example.taskmanager.ui.newtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.databinding.FragmentNewTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class NewTaskFragment() : BottomSheetDialogFragment() {

    private lateinit var dueDate : Date
    private lateinit var currentTask : Task

    companion object {
        const val TAG = "ModalBottomSheet"
    }

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

        binding.imageButton.setOnClickListener{
            binding.calendarView.visibility = if (binding.calendarView.isGone) View.VISIBLE else View.GONE
        }

        if (args.taskId != -1) {

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


            if (args.taskId != -1){
                val editedTask = Task(
                    currentTask._id, binding.etTaskName.text.toString(), calendar.time,
                    dueDate, false
                )
                newTaskViewModel.updateTask(editedTask)
                dismiss()
            }else {
                val newTask = Task(
                    null, binding.etTaskName.text.toString(), calendar.time,
                    dueDate, false
                )
                newTaskViewModel.addTask(newTask)
                dismiss()
            }

        }

        return binding.root
    }


}