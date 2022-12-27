package com.example.gawekerjo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var number = 0

    val currentNumber : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}