package com.example.praktikumppb.network

import com.example.praktikumppb.model.AnimeListResponse
import com.example.praktikumppb.model.AnimeResponse
import com.example.praktikumppb.model.CharacterListResponse
import com.example.praktikumppb.model.CharacterResponse
import com.example.praktikumppb.model.GenreListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnime(@Query("genres") genres: String? = null): AnimeListResponse

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): AnimeResponse

    @GET("top/characters")
    suspend fun getTopCharacters(): CharacterListResponse

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterResponse

    @GET("genres/anime")
    suspend fun getAnimeGenres(): GenreListResponse
}
