package com.appat.squircleavatar.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.appat.graphicov.webservice.service.Resource
import com.appat.squircleavatar.repository.WebServiceRepository
import kotlinx.coroutines.Dispatchers

class PersonsViewModel(private val webServiceRepository: WebServiceRepository) : ViewModel() {
    fun getPersons() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = webServiceRepository.getPersons()
            if(response == null)
            {
                emit(Resource.error(data = null, message = "Error Occurred!"))
            }
            else {
                emit(Resource.success(data = response))
            }
        } catch (exception: Exception) {
            Log.e("getPersons", exception.message.toString())
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}