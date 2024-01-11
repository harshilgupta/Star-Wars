package com.example.star_wars.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data Class for the the results in PeopleDataModel as well the the Data Model for the room database.
 * @author by Harshil Gupta
 */

@Entity(tableName = "people_database")
data class ResultDataModel(
    @PrimaryKey(autoGenerate = true) val id :Int,
    val url: String? = "",
    val birth_year: String? = "",
    val created: String? = "",
    val edited: String? = "",
    val eye_color: String? = "",
    val films: List<String>? = ArrayList(),
    val gender: String? = "",
    val hair_color: String? = "",
    val height: String? = "",
    val homeworld: String? = "",
    val mass: String? = "",
    val name: String? = "",
    val skin_color: String? = "",
    val species: List<String>? = ArrayList(),
    val starships: List<String>? = ArrayList(),
    val vehicles: List<String>? = ArrayList()
)