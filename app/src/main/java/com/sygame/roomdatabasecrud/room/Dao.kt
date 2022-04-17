package com.sygame.roomdatabasecrud.room

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert
    suspend fun addData (user: User)

    @Update
    suspend fun updateData (user: User)

    @Delete
    suspend fun deleteData (user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllData(): List<User>

    @Query("SELECT * FROM user WHERE id=:userId")
    suspend fun getData(userId: Int): List<User>
}