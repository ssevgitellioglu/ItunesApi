package com.example.myapplication.api

import com.example.myapplication.model.MusicProperty
import com.example.myapplication.model.MusicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("search?limit=20")
    fun searchMusic(@Query("term") term: String):
            Call<MusicResponse>
    @GET("search?limit=20")
    fun searchMusicProperty(@Query("term") term: String):
            Call<MusicProperty>
}