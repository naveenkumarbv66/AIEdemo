package com.naveen.aiedemo.view.room.database

import android.content.Context
import androidx.room.*
import com.naveen.aiedemo.view.room.model.TodoTableModel

@Database(entities = [TodoTableModel::class], version = 1, exportSchema = false)
abstract class TodoTaskDatabase : RoomDatabase() {

    abstract fun todoTaskDao(): DatabaseQueryAccess

    companion object {
        @Volatile
        private var INSTANCE: TodoTaskDatabase? = null

        fun getDatabaseClient(context: Context): TodoTaskDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, TodoTaskDatabase::class.java, "TODO_TASK_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }
    }

}