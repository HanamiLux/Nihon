package com.example.nihonhistory.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nihonhistory.models.Celebrate
import com.example.nihonhistory.models.SelectedCelebrate

@Dao
interface SelectedCelebratesDAO {
    /** Get celebrates selected by user*/
    @Query("SELECT Celebrates.* FROM Selected_celebrates JOIN Celebrates" +
            " ON Selected_celebrates.celebrate_id=Celebrates.id WHERE user_id = :userId ")
    suspend fun getSelectedCelebrates(userId: Int): List<Celebrate>
    @Query("SELECT id FROM Selected_celebrates WHERE celebrate_id = :celebrateId ")
    suspend fun getSelectedCelebrateId(celebrateId: Int): Int
    @Insert
    suspend fun addSelectedCelebrate(celebrate: SelectedCelebrate)
    @Delete
    suspend fun deleteSelectedCelebrate(celebrate: SelectedCelebrate)
}