package com.takanakonbu.hobbytime.data.repository

import com.takanakonbu.hobbytime.data.dao.HobbyDao
import com.takanakonbu.hobbytime.data.entity.HobbyEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HobbyRepository @Inject constructor(
    private val hobbyDao: HobbyDao
) {
    fun getAllHobbies(): Flow<List<HobbyEntity>> = hobbyDao.getAllHobbies()

    suspend fun insertHobby(hobby: HobbyEntity) = hobbyDao.insertHobby(hobby)

    suspend fun updateHobby(hobby: HobbyEntity) = hobbyDao.updateHobby(hobby)

    suspend fun deleteHobby(hobby: HobbyEntity) = hobbyDao.deleteHobby(hobby)

    suspend fun getHobbyById(id: Int) = hobbyDao.getHobbyById(id)
}