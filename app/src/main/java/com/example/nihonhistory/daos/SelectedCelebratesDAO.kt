package com.example.nihonhistory.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.nihonhistory.models.Celebrate

@Dao
interface SelectedCelebratesDAO {
    /** Get celebrates selected by user*/
    @Query("SELECT Celebrates.* FROM Selected_celebrates JOIN Celebrates" +
            " ON Selected_celebrates.celebrate_id=Celebrates.id WHERE user_id = :userId ")
    suspend fun getSelectedCelebrates(userId: Int): List<Celebrate>
}