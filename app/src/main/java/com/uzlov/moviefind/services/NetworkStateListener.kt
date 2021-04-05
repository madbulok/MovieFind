package com.uzlov.moviefind.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


class NetworkStateListener(private val networkListener: NetworkListener) : BroadcastReceiver(){
    private lateinit var connectivityManager: ConnectivityManager
    private val TYPE_WIFI = 1
    private val TYPE_MOBILE = 2
    private val TYPE_NOT_CONNECTED = 0
    private val NETWORK_STATUS_NOT_CONNECTED = 0
    private val NETWORK_STATUS_WIFI = 1
    private val NETWORK_STATUS_MOBILE = 2

    override fun onReceive(context: Context, intent: Intent?) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        intent.let {
            if ("android.net.conn.CONNECTIVITY_CHANGE" == intent?.action){
                when(getConnectionState()){
                    NETWORK_STATUS_NOT_CONNECTED-> networkListener.networkStateChanged(false)
                    else -> networkListener.networkStateChanged(true)
                }
            }
        }
    }

    private fun getConnectivityStatus(): Int {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    private fun getConnectionState(): Int {
        return when (getConnectivityStatus()) {
            TYPE_WIFI -> NETWORK_STATUS_WIFI
            TYPE_MOBILE -> NETWORK_STATUS_MOBILE
            TYPE_NOT_CONNECTED -> NETWORK_STATUS_NOT_CONNECTED
            else -> NETWORK_STATUS_NOT_CONNECTED
        }
    }
}
