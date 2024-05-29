package com.example.android.todolistapp

import android.app.Application
import com.example.android.todolistapp.data.PrefsRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.security.auth.Subject

class PrefsRepositoryImplTest {

    private lateinit var subject: PrefsRepositoryImpl
    private val applicationMock : Application = mock()

    @Before
    fun setup() {
        subject = PrefsRepositoryImpl(applicationMock)
    }

    @Test
    fun getTodoItem_success() {
        subject.getTodoItem()
    }

}