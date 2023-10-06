package com.appat.squircleavatar.webservice.service

import com.appat.squircleavatar.R
import com.appat.squircleavatar.utilities.Utility
import com.appat.squircleavatar.webservice.serviceinterface.ServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebService {
    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Utility.getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getService(serviceClass: Class<ServiceInterface>): ServiceInterface {
        return initRetrofit().create(serviceClass)
    }

    fun getWebService(serviceClass: Class<ServiceInterface>): ServiceInterface
    {
        return getService(serviceClass)
    }
}