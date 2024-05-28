package com.example.nihonhistory.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nihonhistory.daos.CelebratesDAO
import com.example.nihonhistory.daos.SelectedCelebratesDAO
import com.example.nihonhistory.daos.TestResultsDAO
import com.example.nihonhistory.daos.UsersDAO
import com.example.nihonhistory.models.Celebrate
import com.example.nihonhistory.models.SelectedCelebrate
import com.example.nihonhistory.models.TestResult
import com.example.nihonhistory.models.User

@Database(entities = [User::class, Celebrate::class, TestResult::class, SelectedCelebrate::class], version = 1, exportSchema = false )
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDAO
    abstract fun celebratesDao(): CelebratesDAO
    abstract fun selectedCelebratesDao(): SelectedCelebratesDAO
    abstract fun testResultsDao(): TestResultsDAO

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDbInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "test.db"
                )
                    .allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }
    }
}