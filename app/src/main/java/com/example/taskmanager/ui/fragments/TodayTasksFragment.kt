package com.example.taskmanager.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTodayTasksBinding
import com.example.taskmanager.ui.adapters.TaskAdapter
import com.example.taskmanager.viewmodels.TaskViewModel
import com.example.taskmanager.viewmodels.TaskViewModelFactory

class TodayTasksFragment : Fragment() {

    private lateinit var binding: FragmentTodayTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_today_tasks, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = TaskViewModelFactory(application)

        val taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        binding.taskViewModel = taskViewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TaskAdapter()
        binding.recyclerView.adapter = adapter

        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.setTaskList(task)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(TodayTasksFragmentDirections.actionTasksListFragmentToNewTaskFragment())
        }

        return binding.root
    }


}