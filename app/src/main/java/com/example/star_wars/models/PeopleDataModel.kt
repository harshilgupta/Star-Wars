package com.example.star_wars.models

/**
 * Data Class for the API call.
 * @author by Harshil Gupta
 */
data class PeopleDataModel(
    val count: Int? = 0,
    val next: String? = "",
    val previous: String? = "",
    val results: ArrayList<ResultDataModel> = arrayListOf()
)