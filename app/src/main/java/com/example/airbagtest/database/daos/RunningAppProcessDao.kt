package com.example.airbagtest.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.airbagtest.database.model.RunningAppProcessCache

@Dao
interface RunningAppProcessDao {
    @Query("SELECT * FROM RunningAppProcessCache")
    suspend fun getAll(): List<RunningAppProcessCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(runningAppProcess: List<RunningAppProcessCache>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(runningAppProcessCacheCaches: RunningAppProcessCache): Long
}