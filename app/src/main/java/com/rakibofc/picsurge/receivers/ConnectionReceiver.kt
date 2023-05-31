package com.rakibofc.picsurge.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

import android.widget.Toast
import com.rakibofc.picsurge.views.MainActivity.Companion.CONNECTIVITY_ACTION


@Suppress("DEPRECATION")
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false

            return when {

                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true

                else -> false
            }

        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}