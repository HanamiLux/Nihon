package com.example.nihonhistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nihonhistory.daos.UsersDAO
import com.example.nihonhistory.models.User

@Database(entities = [User::class], version = 1, exportSchema = false )
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDAO

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