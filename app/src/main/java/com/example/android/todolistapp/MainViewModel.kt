package com.example.android.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.todolistapp.data.RoomManagerImpl

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val roomManager: RoomManager = RoomManagerImpl(app)

    private val todoItemList: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemListResult: LiveData<List<ToDoItem>> = todoItemList

    /**
     * Provides all data from room
     */

    fun getAllData() {
        //TODO deliver data to view layer
        val result = roomManager.getAllItems()
        todoItemList.postValue(result)
    }

    /**
     * Insert Item in room database
     * @param item - provide item that need to be insert in room database
     */

    fun insertItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.plus(item))
            roomManager.insertItem(item)
        }

    }

    /**
     * Updated existing item in room database
     * @param item - provide item that need to be updated in room database
     */

    fun updateItem(item: ToDoItem) {
        val foundIndex = todoItemList.value?.indexOfFirst { it.id ==item.id } //Search for needed item
        foundIndex?.let {
            val list = todoItemList.value?.toMutableList()
            list?.set(it, item) //Updating the item
            todoItemList.value = list!! //Post the final list to live data
        }
    }

    /**
     * Delete existing item from room database
     * @param item - provide item that need to be deleted from room database
     */

    fun deleteItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.minus(item))
            roomManager.deleteItem(item)
        }

    }

}