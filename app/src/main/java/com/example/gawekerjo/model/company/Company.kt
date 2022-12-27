package com.example.gawekerjo.model.company

data class Company(
    val status : Int,
    val data : List<CompanyItem>,
    val message : String,
)