package com.example.star_wars.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.star_wars.app.MyApp

fun isNetworkAvailable(): Boolean {
    val connectivityManager = MyApp.getInstance()?.getSystemService(
        Context.CONNECTIVITY_SERVICE,
    ) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}