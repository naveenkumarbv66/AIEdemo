package com.naveen.aiedemo.view.todolist

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.hadilq.liveevent.LiveEvent
import com.naveen.aiedemo.R
import com.naveen.aiedemo.view.listeners.call
import com.naveen.aiedemo.view.room.model.TodoTableModel
import com.naveen.aiedemo.view.room.repository.TodoTaskRepository

class TodoTaskViewModel : ViewModel() {

    var liveDataTodoTableModelTest = MutableLiveData<List<TodoTableModel>>().apply { value = getIDefaultNoDataMessage() }

    lateinit var selectedTaskObject: TodoTableModel

    private var _createNewTaskOnClick = LiveEvent<Unit>()
    val createNewTaskOnClick: LiveData<Unit> = _createNewTaskOnClick

    private var _updateTaskOnClick = LiveEvent<Unit>()
    val updateTaskOnClick: LiveData<Unit> = _updateTaskOnClick

    private var _deleteTaskOnClick = LiveEvent<Unit>()
    val deleteTaskOnClick: LiveData<Unit> = _deleteTaskOnClick

    lateinit var todoTaskRepository: TodoTaskRepository

    fun insertData(context: Context, taskTile: String, taskInfo: String) {
        todoTaskRepository.saveTodoTask(taskTile, taskInfo, context)
    }

    fun updateData(context: Context, taskTile: String, taskInfo: String, id: Int) {
        todoTaskRepository.updateTodoTask(taskTile, taskInfo, id, context)
    }

    fun deleteData(context: Context, id: Int) {
        todoTaskRepository.deleteTodoTask(id, context)
    }

    fun getData(context: Context): LiveData<List<TodoTableModel>>? {
        return todoTaskRepository.getData(context)
    }

     fun getIDefaultNoDataMessage(): List<TodoTableModel> {
        val items = mutableListOf<TodoTableModel>()
        items.add(TodoTableModel("No data found","Please create a TODO task"))
        return items
    }

    fun createNewTaskScreen(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_DisplayTaskListFragment_to_CreateNewTaskFragment)
    }

    fun saveNewTask() {
        _createNewTaskOnClick.call()
    }

    fun updateTask() {
        _updateTaskOnClick.call()
    }

    fun deleteTask() {
        _deleteTaskOnClick.call()
    }
}