package com.appat.squircleavatar.webservice.api

import com.appat.squircleavatar.webservice.serviceinterface.ServiceInterface

class Api(private val serviceInterface: ServiceInterface) {

    suspend fun getPersons() = serviceInterface.getPersons()

}