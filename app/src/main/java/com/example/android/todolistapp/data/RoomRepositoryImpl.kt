package com.example.android.todolistapp.data

import android.content.Context
import androidx.room.Room
import com.example.android.todolistapp.RoomRepository
import com.example.android.todolistapp.ToDoItem
import com.example.android.todolistapp.room.AppDatabase
import com.example.android.todolistapp.room.ToDoDao
import javax.inject.Inject

/**
 * Manager that handles logic with room data base
 */

class RoomRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao
    ) : RoomRepository {

    override fun getAllItems() : List<ToDoItem> {
        return toDoDao.getAllItems()
    }

    override fun insertItem(item: ToDoItem) {
        toDoDao.insertItem(item)
    }

    override fun updateItem(item: ToDoItem) {
        toDoDao.updateItem(item)
    }

    override fun deleteItem(item: ToDoItem) {
        toDoDao.deleteItem(item)
    }

    companion object {
        const val DATABASE_NAME = "database-name"
    }

}