package com.example.airbagtest.interactors

import com.example.airbagtest.database.model.fromCacheToRemote
import com.example.airbagtest.remote.model.RunningAppProcessRemote
import javax.inject.Inject

class GetBackgroundProcessCacheToRemoteUseCase @Inject constructor(
    private val getBackgroundProcessCacheUseCase: GetBackgroundProcessCacheUseCase
) {

    suspend operator fun invoke(): List<RunningAppProcessRemote>{
        val cacheList = getBackgroundProcessCacheUseCase()
        return List(cacheList.size){ i ->
            cacheList[i].fromCacheToRemote()
        }
    }
}