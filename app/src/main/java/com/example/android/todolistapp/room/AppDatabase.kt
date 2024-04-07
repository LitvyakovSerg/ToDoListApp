package com.example.android.todolistapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.todolistapp.ToDoItem

@Database(entities = [ToDoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ToDoDao
}