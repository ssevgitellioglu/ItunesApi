package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

class MusicResponse (
    @SerializedName("results")
    val musics: List<MusicProperty>
)