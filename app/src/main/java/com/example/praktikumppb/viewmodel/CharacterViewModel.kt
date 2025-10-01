package com.example.praktikumppb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumppb.model.Character
import com.example.praktikumppb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> = _characterList

    private var originalCharacterList = listOf<Character>()

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    fun fetchTopCharacters() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getTopCharacters()
                _characterList.value = response.data
                originalCharacterList = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCharacterById(id: Int) {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getCharacterById(id)
                _character.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchCharacter(query: String) {
        if (query.isEmpty()) {
            _characterList.value = originalCharacterList
        } else {
            _characterList.value = originalCharacterList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    fun sortCharacter(ascending: Boolean) {
        if (ascending) {
            _characterList.value = _characterList.value.sortedBy { it.name }
        } else {
            _characterList.value = _characterList.value.sortedByDescending { it.name }
        }
    }
}
