package com.example.star_wars.repository

import com.example.star_wars.database.PeopleDatabase
import com.example.star_wars.models.PeopleDataModel
import com.example.star_wars.models.ResultDataModel
import com.example.star_wars.network.ApiInterface
import com.example.star_wars.utils.BaseApiResponse
import com.example.star_wars.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
/**
 * Repository for the app to store and provide the data to the UI whether internet is present or not
 * @author by Harshil Gupta
 *
 * @param apiInterface is the Client used to make API Calls in the app
 *
 * @param peopleDatabase is the local Room Database to store and retrieve the People Data
 *
 */

@ActivityRetainedScoped
class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val peopleDatabase: PeopleDatabase
) :
    BaseApiResponse() {

    /**
     * @return this function is used to get the People Data from the API when Internet is available
     */
    suspend fun getAllPeople(
        page: Int
    ): Flow<NetworkResult<PeopleDataModel>> {
        return flow {
            emit(safeApiCall { apiInterface.getAllPeople(page) })
        }.flowOn(Dispatchers.IO)
    }

    /**
     * @return this function is used to get the People Data from the local room database when there is no internet
     */
    suspend fun getSaved(): List<ResultDataModel>{
        return withContext(Dispatchers.IO){
            peopleDatabase.peopleDao().getPeopleList()
        }
    }

    /**
     * @return this function is used to save the fetched data to local room database to use when there is no internet connectivity
     */
    suspend fun saveData(list : List<ResultDataModel>){
        return withContext(Dispatchers.IO){
            if(peopleDatabase.peopleDao().getPeopleList().isNotEmpty())
                peopleDatabase.peopleDao().deleteAll()
            peopleDatabase.peopleDao().insertAll(list)
        }
    }
}