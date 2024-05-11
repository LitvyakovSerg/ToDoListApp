package com.example.android.todolistapp

interface RoomManager {

    fun getAllItems(): List<ToDoItem>
    fun insertItem(item: ToDoItem)
    fun updateItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)

}