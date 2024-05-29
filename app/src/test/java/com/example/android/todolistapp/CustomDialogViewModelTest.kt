package com.example.android.todolistapp

import android.app.Application
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CustomDialogViewModelTest {

    private lateinit var subject : CustomDialogViewModel
    private val prefsRepository : PrefsRepository = mock()

    @Before
    fun setup() {
        subject = CustomDialogViewModel(prefsRepository)
    }

    @Test
    fun getToDoItemFromPrefs_success() {
        //ВСЕ ИНИЦИАЛИЗИРОВАТЬ 1 ЭТАП
        subject.getToDoItemFromPrefs()
        //3 ЭТАП ПРОВЕРКА РЕЗУЛЬТАТОВ
    }
}