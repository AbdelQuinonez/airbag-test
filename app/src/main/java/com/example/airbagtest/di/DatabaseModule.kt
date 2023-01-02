package com.example.airbagtest.di

import android.content.Context
import androidx.room.Room
import com.example.airbagtest.database.AppDatabase
import com.example.airbagtest.utils.Constants.LOCAL_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomInstance(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, LOCAL_DATABASE_NAME
        ).build()

}