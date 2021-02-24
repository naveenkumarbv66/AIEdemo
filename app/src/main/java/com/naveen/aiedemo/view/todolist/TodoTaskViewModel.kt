package com.naveen.aiedemo.view.todolist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naveen.aiedemo.view.room.model.TodoTableModel
import com.naveen.aiedemo.view.room.repository.TodoTaskRepository

class TodoTaskViewModel : ViewModel() {

    var liveDataTodoTableModelTest = MutableLiveData<List<TodoTableModel>>().apply { value = getIDefaultNoDataMessage() }

    lateinit var todoTaskRepository: TodoTaskRepository

    fun insertData(context: Context, taskTile: String, taskInfo: String) {
        todoTaskRepository.saveTodoTask(taskTile, taskInfo, context)
    }

    fun getData(context: Context): LiveData<List<TodoTableModel>>? {
        return todoTaskRepository.getData(context)
    }

     fun getIDefaultNoDataMessage(): List<TodoTableModel> {
        val items = mutableListOf<TodoTableModel>()
        items.add(TodoTableModel("No data found","Please create a TODO task"))
        return items
    }
}