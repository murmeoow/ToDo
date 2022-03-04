package com.example.taskmanager.ui.tasklist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTodayTasksBinding
import com.example.taskmanager.ui.adapters.TaskAdapter
import com.example.taskmanager.ui.newtask.NewTaskFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayTasksFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentTodayTasksBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayTasksBinding.inflate(
            inflater,  container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TaskAdapter({
                taskId ->
                taskViewModel.onTaskClicked(taskId)
            }, { task, isChecked ->
                taskViewModel.onTaskChecked(task, isChecked)
            }, { task ->
                taskViewModel.onTaskDelete(task)
            })

        binding.recyclerView.adapter = adapter


        taskViewModel.navigateToUpdateTask.observe(viewLifecycleOwner, Observer {
            task ->
            task?.let{
                taskViewModel.onTaskUpdateNavigated()
                findNavController().navigate(TodayTasksFragmentDirections.actionTasksListFragmentToNewTaskFragment(task))
            }
        })

        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.submitList(task)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(TodayTasksFragmentDirections.actionTasksListFragmentToNewTaskFragment(-1))
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_task, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        taskViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let{
                adapter.submitList(it)
            }
        })
    }
}