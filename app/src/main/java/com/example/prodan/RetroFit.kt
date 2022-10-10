package com.example.prodan

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFit {
    val baseUrl = "https://prodan-project.herokuapp.com/api/"

        fun getInstance(): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
}