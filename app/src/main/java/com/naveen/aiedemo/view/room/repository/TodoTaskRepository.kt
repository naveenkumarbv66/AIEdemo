package com.naveen.aiedemo.view.room.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.naveen.aiedemo.view.room.database.TodoTaskDatabase
import com.naveen.aiedemo.view.room.model.TodoTableModel

abstract class TodoTaskRepository : Repository {

    lateinit var todoTaskDatabase: TodoTaskDatabase

    abstract fun saveTodoTask(taskName: String, taskInfo: String, taskTime: Long, context: Context)

    abstract fun getData(context: Context): LiveData<List<TodoTableModel>>

    abstract fun updateTodoTask(taskName: String, taskInfo: String, id: Int, taskTime: Long, context: Context)

    abstract fun deleteTodoTask(id: Int, context: Context)

    abstract fun getDataByDate(context: Context): LiveData<List<TodoTableModel>>

    abstract fun updateTodoTaskStatus(isActive: Boolean, id: Int, context: Context)

    abstract fun getTodoTaskExistList(taskName: String, context: Context): LiveData<List<TodoTableModel>>

    private fun initializeDB(context: Context): TodoTaskDatabase {
        return TodoTaskDatabase.getDatabaseClient(context)
    }

    fun initializeDatabase(context: Context) {
        todoTaskDatabase = initializeDB(context)
    }

}