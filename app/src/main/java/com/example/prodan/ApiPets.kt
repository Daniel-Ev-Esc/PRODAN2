package com.example.prodan

import com.example.prodan.data.Pet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiPets {
    @GET("mascotas")
    fun getAllPets(): Call<Pet>

    @GET("mascotas/{id}")
    fun getPetById(@Path("id") id: Int): Call<Pet>

    //Probablemente no se use
    /*
    @POST("pets/{id}")
    fun editPetById(@Path("id") id: Int, @Body pets: pets): Call<pets>*/

}