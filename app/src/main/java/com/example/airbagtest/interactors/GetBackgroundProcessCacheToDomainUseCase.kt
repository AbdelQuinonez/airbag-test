package com.example.airbagtest.interactors

import com.example.airbagtest.database.model.fromCacheToDomain
import com.example.airbagtest.domain.model.RunningAppProcess
import javax.inject.Inject


class GetBackgroundProcessCacheToDomainUseCase @Inject constructor(
    private val getBackgroundProcessCacheUseCase: GetBackgroundProcessCacheUseCase,
    private val insertBackgroundProcessCacheDataBaseUseCase : InsertBackgroundProcessCacheDataBaseUseCase
) {

    suspend operator fun invoke(): List<RunningAppProcess>{
        insertBackgroundProcessCacheDataBaseUseCase()
        val cacheList = getBackgroundProcessCacheUseCase()
        return List(cacheList.size){ i ->
            cacheList[i].fromCacheToDomain()
        }
    }

}