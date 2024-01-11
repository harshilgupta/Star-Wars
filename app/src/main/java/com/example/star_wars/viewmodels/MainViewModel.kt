package com.example.star_wars.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.star_wars.models.PeopleDataModel
import com.example.star_wars.models.ResultDataModel
import com.example.star_wars.repository.MainRepository
import com.example.star_wars.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Main Activity
 * @author by Harshil Gupta
 *
 * This viewmodel is a singleton object and is used throughout the project.
 *
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _allPeopleData: MutableLiveData<NetworkResult<PeopleDataModel>> = MutableLiveData()
    val allPeopleData: LiveData<NetworkResult<PeopleDataModel>> = _allPeopleData
    val peopleList = arrayListOf<ResultDataModel>()

    /**
     * @return this function is used to get the People Data from the API when there is internet connectivity
     */
    fun fetchAllUsers(page: Int) = viewModelScope.launch {
        repository.getAllPeople(page).collect { values ->
            _allPeopleData.value = values
            peopleList.addAll(values.data!!.results)
        }
        viewModelScope.launch {
            repository.saveData(peopleList)
        }
    }

    /**
     * @return this function is used to get the People Data from the room database when there is no internet connectivity
     */
    suspend fun showSaved() {
        peopleList.addAll(repository.getSaved())
    }
}
