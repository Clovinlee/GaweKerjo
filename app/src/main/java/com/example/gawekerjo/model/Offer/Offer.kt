package com.example.gawekerjo.model.Offer

import com.example.gawekerjo.model.follow.FollowItem

class Offer (
    val status : Int,
    val data : List<OfferItem>,
    val message : String,
)