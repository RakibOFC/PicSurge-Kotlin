package com.rakibofc.picsurge.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.rakibofc.picsurge.views.MainActivity.Companion.CONNECTIVITY_ACTION


class ConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val actionOfIntent = intent.action
        val isConnected = checkForInternet(context)

        if (actionOfIntent.equals(CONNECTIVITY_ACTION)) {

            // Get connection status
            val connStatus = if (isConnected) "Connection restored" else "No Connection"

            // Toast the connection status
            Toast.makeText(context, connStatus, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}