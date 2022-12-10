package com.example.gawekerjo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gawekerjo.model.UserItem

class LoginViewModel : ViewModel() {
    var number = 0

    val currentNumber : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}