package com.example.myapplication.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.MusicRepository
import com.example.myapplication.viewmodel.MusicViewModel

class MusicViewModelFactory constructor(private val repository: MusicRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            MusicViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}