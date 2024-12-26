package com.takanakonbu.hobbytime.ui.screens.hobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takanakonbu.hobbytime.data.entity.HobbyEntity
import com.takanakonbu.hobbytime.data.repository.HobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HobbyViewModel @Inject constructor(
    private val repository: HobbyRepository
) : ViewModel() {
    private val _hobbies = MutableStateFlow<List<HobbyEntity>>(emptyList())
    val hobbies: StateFlow<List<HobbyEntity>> = _hobbies

    init {
        viewModelScope.launch {
            repository.getAllHobbies().collect { hobbies ->
                _hobbies.value = hobbies
            }
        }
    }

    fun addHobby(name: String, description: String) {
        viewModelScope.launch {
            val hobby = HobbyEntity(
                name = name,
                description = description
            )
            repository.insertHobby(hobby)
        }
    }

    fun updateHobby(hobby: HobbyEntity) {
        viewModelScope.launch {
            repository.updateHobby(hobby)
        }
    }

    fun deleteHobby(hobby: HobbyEntity) {
        viewModelScope.launch {
            repository.deleteHobby(hobby)
        }
    }
}