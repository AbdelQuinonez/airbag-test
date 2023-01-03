package com.example.airbagtest.remote

import com.example.airbagtest.remote.model.RunningAppProcessRemote
import com.example.airbagtest.utils.AppDispatcher
import com.example.airbagtest.utils.Constants.COLLECTION_NAME
import com.example.airbagtest.utils.Constants.DOCUMENT_NAME
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RunningAppProcessRemoteRepositoryImpl @Inject constructor(
    private val dispatcher: AppDispatcher,
    private val db: FirebaseFirestore,
) : RunningAppProcessRemoteRepository {
    override suspend fun insert(runningAppProcessRemote: List<RunningAppProcessRemote>): Boolean {
        return withContext(dispatcher.io()) {
            suspendCoroutine { continuation ->

                val map = mapOf(android.os.Build.MODEL to runningAppProcessRemote)

                db.collection(COLLECTION_NAME)
                    .document(DOCUMENT_NAME)
                    .set(map)
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