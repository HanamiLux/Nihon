package com.example.nihonhistory.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.nihonhistory.models.Celebrate

@Dao
interface CelebratesDAO {

    /** Get celebrates from today date*/
    @Query("SELECT * FROM Celebrates WHERE startDate > :startDate")
    suspend fun getCelebrates(startDate: Long): List<Celebrate>

}