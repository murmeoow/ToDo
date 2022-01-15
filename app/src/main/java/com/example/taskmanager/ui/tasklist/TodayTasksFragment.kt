package com.example.taskmanager.ui.tasklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTodayTasksBinding
import com.example.taskmanager.ui.adapters.TaskAdapter
import com.example.taskmanager.ui.adapters.TaskClickListener
import com.example.taskmanager.ui.newtask.NewTaskFragment

class TodayTasksFragment : Fragment() {

    private lateinit var binding: FragmentTodayTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayTasksBinding.inflate(
            inflater,  container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = TaskViewModelFactory(application)

        val taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        val bottomSheet = NewTaskFragment()


        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TaskAdapter(TaskClickListener {
            taskId -> taskViewModel.onTaskClicked(taskId)
        }) { task, isChecked ->
            task?.let {
                taskViewModel.onTaskChecked(task, isChecked)
            }

        }
        binding.recyclerView.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = adapter.currentList[viewHolder.adapterPosition]
                taskViewModel.onTaskSwiped(task)
            }

        }).attachToRecyclerView(binding.recyclerView)

        taskViewModel.navigateToUpdateTask.observe(viewLifecycleOwner, Observer {
            task ->
            task?.let{
                val bundle = Bundle()
                bundle.putInt("taskId", task)
                bottomSheet.arguments = bundle
                bottomSheet.show(parentFragmentManager, "ModalBottomSheet")
                taskViewModel.onTaskUpdateNavigated()
            }
        })

        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.submitList(task)
        })

        binding.floatingActionButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("taskId", -1)
            bottomSheet.arguments = bundle
            bottomSheet.show(parentFragmentManager, "ModalBottomSheet")
        }

        return binding.root
    }


}