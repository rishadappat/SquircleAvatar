package com.appat.squircleavatar.models

data class Persons(
    val code: Int,
    val data: List<PersonsData>,
    val status: String,
    val total: Int
)

data class PersonsData(
    val address: Address,
    val birthday: String,
    val email: String,
    val firstname: String,
    val gender: String,
    val image: String,
    val lastname: String,
    val phone: String,
    val website: String
)

data class Address(
    val buildingNumber: String,
    val city: String,
    val country: String,
    val county_code: String,
    val latitude: Double,
    val longitude: Double,
    val street: String,
    val streetName: String,
    val zipcode: String
)