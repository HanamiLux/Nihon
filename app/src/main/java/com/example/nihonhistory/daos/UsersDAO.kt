package com.example.nihonhistory.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nihonhistory.models.User

@Dao
interface UsersDAO {
    @Insert
    suspend fun insertUser(order: User)

    @Update
    suspend fun updateUser(order: User)

    @Query("SELECT * FROM Users WHERE email = :userEmail")
    suspend fun getUserByEmail(userEmail: String): User?

}