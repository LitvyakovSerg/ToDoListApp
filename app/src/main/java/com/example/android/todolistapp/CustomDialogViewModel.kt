package com.example.android.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.todolistapp.data.PrefsManagerImpl

class CustomDialogViewModel(app: Application) : AndroidViewModel(app) {

    private val prefsManager: PrefsManager = PrefsManagerImpl(app)
    
    private val todoItem: MutableLiveData<ToDoItem> = MutableLiveData()
    val todoItemResult: LiveData<ToDoItem> = todoItem

    /**
     * Provides preferences values for ToDo item
     */

    fun getToDoItemFromPrefs() {
        val result = prefsManager.getTodoItem()
        todoItem.postValue(result)
    }

    /**
     * Save data in shared preferences manager
     * @param key - provide prefs information to save data
     * @param value - provide data that need to be saved in prefs
     */

    fun saveDataInPrefs(key: String, value: String) {
        prefsManager.saveDataInPrefs(key, value)
    }

}