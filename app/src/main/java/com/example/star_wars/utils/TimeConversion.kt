package com.example.star_wars.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun convertTime(date: String?): String {
    return try {
        val initDate =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(date)!!
        val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        formatter.format(initDate)
    } catch (e: Exception) {
        date.toString()
    }
}