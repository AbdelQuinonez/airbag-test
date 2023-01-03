package com.example.airbagtest.remote

import com.example.airbagtest.database.model.RunningAppProcessCache
import com.example.airbagtest.utils.AppDispatcher
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RunningAppProcessRemoteRepositoryImpl @Inject constructor(
    private val dispatcher: AppDispatcher,
    private val db: FirebaseFirestore,
) : RunningAppProcessRemoteRepository {
    override suspend fun insert(runningAppProcessCache: List<RunningAppProcessCache>): Boolean {
        return withContext(dispatcher.io()) {
            suspendCoroutine { continuation ->
                db.collection(RunningAppProcessCache::class.simpleName.toString())
                    .document(android.os.Build.MODEL)
                    .set(runningAppProcessCache)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    }
            }
        }
    }
}