package com.naveen.aiedemo.view.listeners

import com.naveen.aiedemo.view.room.model.TodoTableModel

interface RecyclerViewListener{
    fun onClick(todoTaskObject: TodoTableModel)
}