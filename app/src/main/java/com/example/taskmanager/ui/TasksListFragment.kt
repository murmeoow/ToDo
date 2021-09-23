package com.example.taskmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentTasksListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tasks_list, container, false)



        return binding.root
    }


}