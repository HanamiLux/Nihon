package com.example.nihonhistory.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nihonhistory.models.User

@Dao
interface UsersDAO {
    @Insert
    fun insertUser(order: User)

    @Update
    fun updateUser(order: User)

    @Query("SELECT * FROM Users WHERE login = :userLogin")
    fun getUserByLogin(userLogin: String): User

}