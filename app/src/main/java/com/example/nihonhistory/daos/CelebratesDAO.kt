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
    @Query("""
    SELECT * FROM Celebrates 
    WHERE (:name IS NULL OR name LIKE '%' || :name || '%') 
    AND (:startDate = 0 OR startDate >= :startDate)
    AND (:endDate = 0 OR endDate <= :endDate)
    AND ((:filterSpring = 0 AND (strftime('%m', startDate / 1000, 'unixepoch') BETWEEN '03' AND '05'))
    OR (:filterSummer = 0 AND (strftime('%m', startDate / 1000, 'unixepoch') BETWEEN '06' AND '08'))
    OR (:filterAutumn = 0 AND (strftime('%m', startDate / 1000, 'unixepoch') BETWEEN '09' AND '11'))
    OR (:filterWinter = 0 AND (strftime('%m', startDate / 1000, 'unixepoch') IN ('12', '01', '02'))))
""")
    suspend fun getCelebratesByFilters(
        name: String?,
        endDate: Long,
        startDate: Long,
        filterSpring: Boolean,
        filterSummer: Boolean,
        filterAutumn: Boolean,
        filterWinter: Boolean
    ): List<Celebrate>

    @Transaction
    suspend fun clearAllCelebrates(){
        removeAllSelectedCelebrates()
        removeAllCelebrates()
    }

}