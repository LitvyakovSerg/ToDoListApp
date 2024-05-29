package com.example.android.todolistapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.security.auth.Subject

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject: MainViewModel
    private val applicationMock : Application = mock()

    private val itemTest = ToDoItem(0, "testTitle", "testDescription", "testNumber")

    @Before
    fun setup() {
        subject = MainViewModel(applicationMock)
    }

    @Test
    fun insertItem_success() {
        subject.insertItem(itemTest)
        // 3 проверка получил ли room manager элемент с заголовком testTitle
    }

}