package com.naveen.aiedemo.view.todolist

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.hadilq.liveevent.LiveEvent
import com.naveen.aiedemo.R
import com.naveen.aiedemo.view.listeners.LongToDateString
import com.naveen.aiedemo.view.listeners.call
import com.naveen.aiedemo.view.room.model.TodoTableModel
import com.naveen.aiedemo.view.room.repository.TodoTaskRepository
import java.text.SimpleDateFormat
import java.util.*

class TodoTaskViewModel : ViewModel() {

    var liveDataTodoTableModelTest = MutableLiveData<List<TodoTableModel>>().apply { value = getIDefaultNoDataMessage() }

    lateinit var selectedTaskObject: TodoTableModel

    val userSelectedDateTime = MutableLiveData<Date>().apply { value = Date() }

    private var _createNewTaskOnClick = LiveEvent<Unit>()
    val createNewTaskOnClick: LiveData<Unit> = _createNewTaskOnClick

    private var _updateTaskOnClick = LiveEvent<Unit>()
    val updateTaskOnClick: LiveData<Unit> = _updateTaskOnClick

    private var _deleteTaskOnClick = LiveEvent<Unit>()
    val deleteTaskOnClick: LiveData<Unit> = _deleteTaskOnClick

    private var _dateTimePickerOnClick = LiveEvent<Unit>()
    val dateTimePickerOnClick: LiveData<Unit> = _dateTimePickerOnClick

    lateinit var todoTaskRepository: TodoTaskRepository

    fun insertData(context: Context, taskTile: String, taskInfo: String, taskTime: Long) {
        todoTaskRepository.saveTodoTask(taskTile, taskInfo, taskTime, context)
    }

    fun updateData(context: Context, taskTile: String, taskInfo: String, id: Int, taskTime: Long) {
        todoTaskRepository.updateTodoTask(taskTile, taskInfo, id, taskTime, context)
    }

    fun deleteData(context: Context, id: Int) {
        todoTaskRepository.deleteTodoTask(id, context)
    }

    fun getData(context: Context): LiveData<List<TodoTableModel>>? {
        return todoTaskRepository.getData(context)
    }

    fun getDataByDate(context: Context): LiveData<List<TodoTableModel>>? {
        return todoTaskRepository.getDataByDate(context)
    }

     fun getIDefaultNoDataMessage(): List<TodoTableModel> {
        val items = mutableListOf<TodoTableModel>()
        items.add(TodoTableModel("No data found","Please create a TODO task", 0.toLong(), false))
        return items
    }

    fun createNewTaskScreen(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_DisplayTaskListFragment_to_CreateNewTaskFragment)
    }

    fun getDateInString(date: Date) : String {
        val sdf = SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault())
        return sdf.format(date)
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

    fun dateTimePickerClick() {
        _dateTimePickerOnClick.call()
    }

    fun updateTaskStatus(latestData: List<TodoTableModel>, context: Context): List<TodoTableModel>{
        latestData?.forEachIndexed { _: Int, todoTableModel: TodoTableModel ->
            if (Calendar.getInstance().time.after(Date(todoTableModel.TaskTime)) && todoTableModel.IsActive) {
                todoTaskRepository.updateTodoTaskStatus(!todoTableModel.IsActive, todoTableModel.Id ?: 0, context)
            }else if (Calendar.getInstance().time.before(Date(todoTableModel.TaskTime)) && !todoTableModel.IsActive) {
                todoTaskRepository.updateTodoTaskStatus(!todoTableModel.IsActive, todoTableModel.Id ?: 0, context)
            }
        }
        return latestData
    }

}