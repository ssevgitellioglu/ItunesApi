package com.example.myapplication.repository

import com.example.myapplication.api.ApiService

class MusicRepository ( val service: ApiService,val term:String) {

    fun getAllMusic() = service.searchMusic(term)
}