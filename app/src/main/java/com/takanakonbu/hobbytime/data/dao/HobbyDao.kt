package com.takanakonbu.hobbytime.data.dao

import androidx.room.*
import com.takanakonbu.hobbytime.data.entity.HobbyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HobbyDao {
    @Query("SELECT * FROM hobbies ORDER BY name ASC")
    fun getAllHobbies(): Flow<List<HobbyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHobby(hobby: HobbyEntity)

    @Update
    suspend fun updateHobby(hobby: HobbyEntity)

    @Delete
    suspend fun deleteHobby(hobby: HobbyEntity)

    @Query("SELECT * FROM hobbies WHERE id = :hobbyId")
    suspend fun getHobbyById(hobbyId: Int): HobbyEntity?
}