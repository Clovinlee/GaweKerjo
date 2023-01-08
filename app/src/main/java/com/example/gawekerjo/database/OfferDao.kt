package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.Offer.OfferItem

@Dao
interface OfferDao {
    @Insert
    suspend fun insertOffer(offer: OfferItem)
    @Delete
    suspend fun deleteOffer(offer: OfferItem)
    @Update
    suspend fun updateOffer(offer: OfferItem)

    @Query("DELETE FROM offers")
    suspend fun clear()

    @Query("SELECT * FROM offers")
    suspend fun fetch():List<OfferItem>
}