package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.OfferItem

@Dao
interface OfferDao {
    @Insert
    suspend fun insertOffer(offer: OfferItem)
    @Delete
    suspend fun deleteOffer(offer: OfferItem)
    @Update
    suspend fun updateOffer(offer: OfferItem)
    @Query("SELECT * FROM offers")
    suspend fun getAllOffer():List<OfferItem>
}