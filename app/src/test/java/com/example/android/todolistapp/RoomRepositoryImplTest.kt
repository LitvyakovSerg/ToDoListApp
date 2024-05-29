package com.example.android.todolistapp

import android.app.Application
import android.content.Context
import com.example.android.todolistapp.data.RoomRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.security.auth.Subject

class RoomRepositoryImplTest {

    private lateinit var subject: RoomRepositoryImpl
    private val contextMock : Context = mock()

    @Before
    fun setup() {
        subject = RoomRepositoryImpl(contextMock)
    }

    @Test
    fun getAllItems_success() {
        subject.getAllItems()
    }
}