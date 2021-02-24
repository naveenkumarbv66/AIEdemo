package com.naveen.aiedemo.view.room.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.naveen.aiedemo.view.room.model.TodoTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoTaskRepositoryImpl : TodoTaskRepository() {
    override fun saveTodoTask(taskName: String, taskInfo: String, context: Context) {
        initializeDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val loginDetails = TodoTableModel(taskName, taskInfo)
            todoTaskDatabase.todoTaskDao().insertTodoTask(loginDetails)
        }
    }

    override fun getData(context: Context): LiveData<List<TodoTableModel>> {
        initializeDatabase(context)
        return todoTaskDatabase.todoTaskDao().getTodoTaskList()
    }
}