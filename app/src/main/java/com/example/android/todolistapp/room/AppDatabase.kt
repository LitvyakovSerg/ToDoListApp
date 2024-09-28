package com.example.android.todolistapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.todolistapp.ToDoItem

@Database(entities = [ToDoItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}