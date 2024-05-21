package com.example.android.todolistapp.data

import android.content.Context
import androidx.room.Room
import com.example.android.todolistapp.RoomManager
import com.example.android.todolistapp.ToDoItem
import com.example.android.todolistapp.room.AppDatabase

/**
 * Manager that handles logic with room data base
 */

class RoomManagerImpl(private val context: Context) : RoomManager {
        private var db = Room.databaseBuilder(
            context,
    AppDatabase::class.java, DATABASE_NAME
    )
    .allowMainThreadQueries()
    .build()

    override fun getAllItems() : List<ToDoItem> {
        return db.userDao().getAllItems()
    }

    override fun insertItem(item: ToDoItem) {
        db.userDao().insertItem(item)
    }

    override fun updateItem(item: ToDoItem) {
        db.userDao().updateItem(item)
    }

    override fun deleteItem(item: ToDoItem) {
        db.userDao().deleteItem(item)
    }

    companion object {
        private const val DATABASE_NAME = "database-name"
    }

}