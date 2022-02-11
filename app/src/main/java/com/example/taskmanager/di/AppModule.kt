package com.example.taskmanager.di

import android.content.Context
import com.example.taskmanager.data.TaskDatabase
import com.example.taskmanager.data.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase{
        return TaskDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDao(taskDatabase: TaskDatabase): TaskDao{
        return taskDatabase.taskDao
    }


}