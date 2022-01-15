package com.example.taskmanager.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.Utils
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.databinding.TaskItemBinding


class TaskAdapter(val taskClickListener: TaskClickListener,
                    val checkBoxClickListener: (Task, Boolean) -> Unit): ListAdapter<Task, TaskAdapter.TaskHolder>(TaskDiffCallback())  {

    inner class TaskHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = TaskItemBinding.bind(item)


        fun bind(task: Task, taskClickListener: TaskClickListener) = with(binding){

            val formatted = Utils.formatDate(task.taskDueDate)
            taskVar = task
            clickListener = taskClickListener
            tvTaskName.text = task.taskName
            tvTaskName.paint.isStrikeThruText = task.status
            tvTaskDate.text = formatted
            checkBox.isChecked = task.status
            checkBox.setOnClickListener {
                checkBoxClickListener(task, checkBox.isChecked)
            }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskHolder, position: Int) {
        holder.bind(getItem(position)!!, taskClickListener)
    }


}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}


class TaskClickListener(val clickListener : (taskId : Int ) -> Unit){
    fun onClick(task: Task) = task._id?.let { clickListener(it) }
}
