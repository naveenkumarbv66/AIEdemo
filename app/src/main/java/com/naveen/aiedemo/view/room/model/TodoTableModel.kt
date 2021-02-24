package com.naveen.aiedemo.view.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodoTaskTable")
data class TodoTableModel(

    @ColumnInfo(name = "taskTitle")
    var TaskTitle: String,

    @ColumnInfo(name = "taskInfo")
    var TaskInfo: String

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}