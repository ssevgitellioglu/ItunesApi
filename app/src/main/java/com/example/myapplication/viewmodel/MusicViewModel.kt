package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.MusicProperty
import com.example.myapplication.model.MusicResponse
import com.example.myapplication.repository.MusicRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel ()  : ViewModel() {

    val musicList = MutableLiveData<ArrayList<MusicProperty>>()

    fun getAllMusics(): MutableLiveData<ArrayList<MusicProperty>> {
        val repository: MusicRepository?=null
        val response = repository?.getAllMusic()
        response?.enqueue(object : Callback<MusicResponse> {
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                musicList.postValue(response.body()?.musics as ArrayList<MusicProperty>?)
            }

            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {

            }
        })
        return musicList
    }
}