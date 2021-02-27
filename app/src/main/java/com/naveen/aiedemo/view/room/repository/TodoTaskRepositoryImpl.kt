package com.naveen.aiedemo.view.room.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.naveen.aiedemo.view.room.model.TodoTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoTaskRepositoryImpl : TodoTaskRepository() {
    override fun saveTodoTask(taskName: String, taskInfo: String, taskTime: Long, context: Context) {
        initializeDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val todoTaskDetails = TodoTableModel(taskName, taskInfo, taskTime, true)
            todoTaskDatabase.todoTaskDao().insertTodoTask(todoTaskDetails)
        }
    }

    override fun getData(context: Context): LiveData<List<TodoTableModel>> {
        initializeDatabase(context)
        return todoTaskDatabase.todoTaskDao().getTodoTaskList()
    }

    override fun updateTodoTask(taskName: String, taskInfo: String, id: Int, taskTime: Long, context: Context) {
        initializeDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            todoTaskDatabase.todoTaskDao().updateTodoTask(taskName, taskInfo, taskTime, id)
        }
    }

    override fun deleteTodoTask(id: Int, context: Context) {
        initializeDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            todoTaskDatabase.todoTaskDao().deleteTodoTask(id)
        }
    }

    override fun getDataByDate(context: Context): LiveData<List<TodoTableModel>> {
        initializeDatabase(context)
        return todoTaskDatabase.todoTaskDao().getTodoTaskListByDate()
    }

    override fun updateTodoTaskStatus(isActive: Boolean, id: Int, context: Context) {
        initializeDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            todoTaskDatabase.todoTaskDao().updateActiveStatus(isActive, id)
        }
    }
}