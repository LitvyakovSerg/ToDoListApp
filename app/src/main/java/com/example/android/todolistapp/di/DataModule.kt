package com.example.android.todolistapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.android.todolistapp.PrefsRepository
import com.example.android.todolistapp.RoomRepository
import com.example.android.todolistapp.data.PrefsRepositoryImpl
import com.example.android.todolistapp.data.PrefsRepositoryImpl.Companion.PREFS_NAME
import com.example.android.todolistapp.data.RoomRepositoryImpl
import com.example.android.todolistapp.data.RoomRepositoryImpl.Companion.DATABASE_NAME
import com.example.android.todolistapp.room.AppDatabase
import com.example.android.todolistapp.room.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
        AppDatabase::class.java, DATABASE_NAME
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesToDoDao(appDatabase: AppDatabase) = appDatabase.toDoDao()

    @Singleton
    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context) : SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providesRoomRepository(toDoDao: ToDoDao) : RoomRepository =
        RoomRepositoryImpl(toDoDao)

    @Singleton
    @Provides
    fun providesPrefsRepository(sharedPreferences: SharedPreferences) : PrefsRepository =
        PrefsRepositoryImpl(sharedPreferences)
}