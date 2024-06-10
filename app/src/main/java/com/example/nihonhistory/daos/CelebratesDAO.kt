package com.example.nihonhistory.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nihonhistory.models.Celebrate

@Dao
interface CelebratesDAO {

    /** Get celebrates from today date*/
    @Query("SELECT * FROM Celebrates WHERE startDate > :startDate")
    suspend fun getCelebrates(startDate: Long): List<Celebrate>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCelebrates(holidays: List<Celebrate>)
    @Query("DELETE FROM Celebrates")
    suspend fun removeAllCelebrates()
    @Query("DELETE FROM Selected_celebrates")
    suspend fun removeAllSelectedCelebrates()
    @Transaction
    suspend fun clearAllCelebrates(){
        removeAllSelectedCelebrates()
        removeAllCelebrates()
    }

}