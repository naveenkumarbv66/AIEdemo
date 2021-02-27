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

    @Query("UPDATE TodoTaskTable SET TaskTitle = :taskTitle, TaskInfo= :taskInfo, TaskTime= :taskTime WHERE Id =:id")
    suspend fun updateTodoTask(taskTitle: String, taskInfo: String, taskTime: Long, id: Int)

    @Query("DELETE FROM TodoTaskTable WHERE id = :id")
    suspend fun deleteTodoTask(id: Int)

    @Query("SELECT * FROM TodoTaskTable ORDER BY taskTime")
    fun getTodoTaskListByDate(): LiveData<List<TodoTableModel>>

    @Query("UPDATE TodoTaskTable SET IsActive = :isActive WHERE Id =:id")
    suspend fun updateActiveStatus(isActive: Boolean, id: Int)

    @Query("SELECT * FROM TodoTaskTable WHERE TaskTitle = :taskTitle AND TaskTime = :taskTime")
    fun getTodoTaskExistList(taskTitle: String, taskTime: Long): LiveData<List<TodoTableModel>>

}