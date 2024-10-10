package com.example.android.todolistapp.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.android.todolistapp.PrefsManager
import com.example.android.todolistapp.ToDoItem

/**
 * Manager that handles logic with shared preferences
 */

class   PrefsManagerImpl(app: Application) : PrefsManager {

    private val sharedPref: SharedPreferences = app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getTodoItem(): ToDoItem {
        // Если в sharedPref не будет данных по ключу или самого ключа, то возвращаем пустую строку
        val title = sharedPref.getString(PREFS_TITLE_KEY, PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
        val description = sharedPref.getString(PREFS_DESCRIPTION_KEY, PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
//        val number = sharedPref.getString(PREFS_NUMBER_KEY, PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
//        return ToDoItem(0, title, description, number)
        return ToDoItem(0, title, description)
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    companion object {
        const val PREFS_TITLE_KEY = "titleKey"
        const val PREFS_DESCRIPTION_KEY = "descriptionKey"
//        const val PREFS_NUMBER_KEY = "numberKey"
        const val PREFS_NAME = "preferences"
        const val PREFS_DEFAULT_VALUE = ""
    }

}