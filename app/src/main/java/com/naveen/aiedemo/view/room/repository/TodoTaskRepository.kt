package com.naveen.aiedemo.view.room.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.naveen.aiedemo.view.room.database.TodoTaskDatabase
import com.naveen.aiedemo.view.room.model.TodoTableModel

abstract class TodoTaskRepository : Repository {

    var todoTaskDatabase: TodoTaskDatabase? = null
    var todoTaskTableModel: LiveData<List<TodoTableModel>>? = null

    abstract fun saveTodoTask(taskName: String, taskInfo: String, context: Context)

    abstract fun getData(context: Context): LiveData<List<TodoTableModel>>?

    private fun initializeDB(context: Context): TodoTaskDatabase {
        return TodoTaskDatabase.getDatabaseClient(context)
    }

    fun initializeDatabase(context: Context) {
        todoTaskDatabase = initializeDB(context)
    }

}