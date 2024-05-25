package com.example.nihonhistory.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.nihonhistory.models.TestResult

@Dao
interface TestResultsDAO {
    /** Get user results of tests */
    @Query("SELECT * FROM Test_results WHERE user_id = :userId")
    suspend fun getTestResults(userId: Int): TestResult
}