package com.example.gawekerjo.model.country

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CurrencyDetail(
    val name: String?,
    val symbol: String?
) :Parcelable