package com.example.star_wars.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.star_wars.models.ResultDataModel

/**
 * Data Access Object for the PeopleDatabase class
 * @author by Harshil Gupta
 *
 * This interface contains the functions to be performed on the database
 *
 */

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(peopleList: List<ResultDataModel>)

    @Query("DELETE from people_database")
    suspend fun deleteAll()

    @Query("SELECT * from people_database")
    fun getPeopleList(): List<ResultDataModel>

}