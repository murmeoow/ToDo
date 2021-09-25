package com.example.taskmanager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.databinding.TaskItemBinding

class TaskAdapter(): RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var taskList = listOf<Task>()

    class TaskHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = TaskItemBinding.bind(item)

        fun bind(task : Task) = with(binding){

            tvTaskName.text = task.taskName
            tvTaskDescriptionList.text = task.taskDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount() = taskList.size

    fun setTaskList(task : List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }

}
