package com.example.star_wars.utils
/**
 * Network Result Class
 * @author by Harshil Gupta
 *
 * This class is used for safe API calls and to perform actions based on the response from API
 *
 * @param data is the data received from the API, and
 * @param message is the API status message for the API call
 */
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
}
