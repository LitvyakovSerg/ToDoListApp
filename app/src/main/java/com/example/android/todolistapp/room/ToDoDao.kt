package com.example.android.todolistapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.todolistapp.ToDoItem

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todoitem") //Здесь может быть ошибка
    fun getAllItems(): List<ToDoItem>

    @Insert
     fun insertItem(toDoItem: ToDoItem)

    @Delete
     fun deleteItem(toDoItem: ToDoItem)

    @Update
    fun updateItem(toDoItem: ToDoItem)
}