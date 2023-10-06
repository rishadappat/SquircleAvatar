package com.appat.squircleavatar.webservice.serviceinterface

import com.appat.squircleavatar.models.Persons
import retrofit2.http.GET

interface ServiceInterface {
    @GET("persons")
    suspend fun getPersons(): Persons
}