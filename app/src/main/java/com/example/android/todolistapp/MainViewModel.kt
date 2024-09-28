package com.example.android.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todolistapp.data.RoomRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val todoItemList: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemListResult: LiveData<List<ToDoItem>> = todoItemList

    /**
     * Provides all data from room
     */

    fun getAllData() {
        val result = roomRepository.getAllItems()
        todoItemList.postValue(result)
    }

    /**
     * Insert Item in room database
     * @param item - provide item that need to be insert in room database
     */

    fun insertItem(item: ToDoItem) {
        todoItemList.value.let {
            todoItemList.postValue(it?.plus(item))
            roomRepository.insertItem(item)
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
            roomRepository.deleteItem(item)
        }

    }

}