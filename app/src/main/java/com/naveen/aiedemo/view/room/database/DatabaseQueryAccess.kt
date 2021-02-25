package com.naveen.aiedemo.view.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.naveen.aiedemo.view.room.model.TodoTableModel

@Dao
interface DatabaseQueryAccess {
    @Insert
    suspend fun insertTodoTask(todoTableModel: TodoTableModel)

    @Query("SELECT * FROM TodoTaskTable")
    fun getTodoTaskList(): LiveData<List<TodoTableModel>>

    @Query("UPDATE TodoTaskTable SET TaskTitle = :taskTitle, TaskInfo= :taskInfo WHERE Id =:id")
    suspend fun updateTodoTask(taskTitle: String, taskInfo: String, id: Int)

    @Query("DELETE FROM TodoTaskTable WHERE id = :id")
    suspend fun deleteTodoTask(id: Int)

}