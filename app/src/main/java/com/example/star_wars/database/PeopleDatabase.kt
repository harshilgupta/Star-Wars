package com.example.star_wars.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.star_wars.models.ResultDataModel
import com.example.star_wars.utils.Converters

/**
 * Database Class for the Room Database
 * @author by Harshil Gupta
 *
 * This class provides the instance of Room Database for the app to store/fetch the data from the database via Repository.
 *
 */

@Database(entities = [ResultDataModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PeopleDatabase : RoomDatabase() {

    abstract fun peopleDao(): PeopleDao

    companion object {
        @Volatile
        private var INSTANCE: PeopleDatabase? = null
        fun getDatabase(context: Context): PeopleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, PeopleDatabase::class.java, "people_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
