package com.appat.squircleavatar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appat.squircleavatar.repository.WebServiceRepository
import com.appat.squircleavatar.webservice.api.Api

class ViewModelFactory(private val api: Api) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PersonsViewModel::class.java) -> {
                PersonsViewModel(WebServiceRepository(api)) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }
}