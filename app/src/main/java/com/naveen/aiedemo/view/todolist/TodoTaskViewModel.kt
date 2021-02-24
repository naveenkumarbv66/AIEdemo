package com.naveen.aiedemo.view.todolist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naveen.aiedemo.view.room.model.TodoTableModel
import com.naveen.aiedemo.view.room.repository.TodoTaskRepository

class TodoTaskViewModel : ViewModel() {

    var liveDataTodoTableModel: LiveData<List<TodoTableModel>>? = null
    lateinit var todoTaskRepository: TodoTaskRepository

    fun insertData(context: Context, taskTile: String, taskInfo: String) {
        todoTaskRepository.saveTodoTask(taskTile, taskInfo, context)
    }

    fun getData(context: Context): LiveData<List<TodoTableModel>>? {
        liveDataTodoTableModel = todoTaskRepository.getData(context)
        return liveDataTodoTableModel
    }
}