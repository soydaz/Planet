package com.example.planet.core.storage.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.planet.core.storage.local.room.entitys.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM users_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM users_table")
    suspend fun getUsersList(): List<User>

}