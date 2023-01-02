package com.example.airbagtest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.airbagtest.database.daos.RunningAppProcessDao
import com.example.airbagtest.database.model.RunningAppProcessCache

@Database(entities = [RunningAppProcessCache::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun runningAppProcess(): RunningAppProcessDao
}