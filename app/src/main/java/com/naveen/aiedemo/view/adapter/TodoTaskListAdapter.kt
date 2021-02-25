package com.naveen.aiedemo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.TodoTaskRowBinding
import com.naveen.aiedemo.view.listeners.RecyclerViewListener
import com.naveen.aiedemo.view.room.model.TodoTableModel
import java.util.*

class TodoTaskListAdapter(private val listener: RecyclerViewListener) : RecyclerView.Adapter<ViewHolder>() {

    private var todoTaskList: List<TodoTableModel> = emptyList()

    companion object {
        @JvmStatic
        @BindingAdapter("todoTaskList")
        fun RecyclerView.bindItems(todoTaskList: List<TodoTableModel>) {
            val adapter = adapter as TodoTaskListAdapter
            adapter.update(todoTaskList)
        }
    }

    fun update(todoTaskList: List<TodoTableModel>) {
        this.todoTaskList = todoTaskList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && todoTaskList.size > position) {
            holder.bind(todoTaskList[position], listener)
        }
    }

    override fun getItemCount()  = todoTaskList.size

    class ItemViewHolder(
        private val parent: ViewGroup,
        private val binding: TodoTaskRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.todo_task_row,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: TodoTableModel, listener: RecyclerViewListener) {
            binding.todoTableModel = item
            binding.listener = listener
        }
    }

}