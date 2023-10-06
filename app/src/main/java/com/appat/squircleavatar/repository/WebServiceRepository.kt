package com.appat.squircleavatar.repository

import android.util.Log
import com.appat.squircleavatar.models.Persons
import com.appat.squircleavatar.webservice.api.Api

class WebServiceRepository(private val api: Api) {

    suspend fun getPersons(): Persons? {
        return try {
            api.getPersons()
        } catch (e: Exception)
        {
            Log.e("getPersons", e.toString())
            return null
        }
    }
}