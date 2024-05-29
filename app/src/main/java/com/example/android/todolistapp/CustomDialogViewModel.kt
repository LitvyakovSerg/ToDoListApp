package com.example.android.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todolistapp.data.PrefsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomDialogViewModel @Inject constructor (
    private val prefsRepository: PrefsRepository
        ) : ViewModel() {

    private val todoItem: MutableLiveData<ToDoItem> = MutableLiveData()
    val todoItemResult: LiveData<ToDoItem> = todoItem

    /**
     * Provides preferences values for ToDo item
     */

    fun getToDoItemFromPrefs() {
        val result = prefsRepository.getTodoItem()
        todoItem.postValue(result)
    }

    /**
     * Save data in shared preferences manager
     * @param key - provide prefs information to save data
     * @param value - provide data that need to be saved in prefs
     */

    fun saveDataInPrefs(key: String, value: String) {
        prefsRepository.saveDataInPrefs(key, value)
    }

}