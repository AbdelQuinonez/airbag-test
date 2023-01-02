package com.example.airbagtest.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkApiImpl @Inject constructor(@ApplicationContext val context: Context) : NetworkApi {

    private val connectivityManager: ConnectivityManager
            by lazy { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    override fun connectivityStatus(): Flow<NetworkApi.Status> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(NetworkApi.Status.Available) }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                launch { send(NetworkApi.Status.Losing) }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(NetworkApi.Status.Lost) }
            }

            override fun onUnavailable() {
                super.onUnavailable()
                launch { send(NetworkApi.Status.Unavailable) }
            }

        }
        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

}