package com.example.star_wars.network

import com.example.star_wars.models.PeopleDataModel
import retrofit2.Response
import retrofit2.http.*

/**
 * API interface to call 'people' API.
 * @author by Harshil Gupta
 */

interface ApiInterface {
    @GET("people")
    suspend fun getAllPeople(
        @Query("page") page: Int
    ): Response<PeopleDataModel>
}
