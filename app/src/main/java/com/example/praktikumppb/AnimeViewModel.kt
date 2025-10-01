package com.example.praktikumppb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumppb.model.Anime
import com.example.praktikumppb.model.Genre
import com.example.praktikumppb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList

    private var originalAnimeList = listOf<Anime>()

    private val _anime = MutableStateFlow<Anime?>(null)
    val anime: StateFlow<Anime?> = _anime

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genres

    fun fetchTopAnime(genreId: Int? = null) {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getTopAnime(genres = genreId?.toString())
                _animeList.value = response.data
                originalAnimeList = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchAnimeById(id: Int) {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getAnimeById(id)
                _anime.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchAnimeGenres() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.getAnimeGenres()
                _genres.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchAnime(query: String) {
        if (query.isEmpty()) {
            _animeList.value = originalAnimeList
        } else {
            _animeList.value = originalAnimeList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }

    fun sortAnime(ascending: Boolean) {
        if (ascending) {
            _animeList.value = _animeList.value.sortedBy { it.title }
        } else {
            _animeList.value = _animeList.value.sortedByDescending { it.title }
        }
    }
}
