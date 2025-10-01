package com.example.praktikumppb.model

data class GenreListResponse(
    val data: List<Genre>
)

data class Genre(
    val mal_id: Int,
    val name: String
)
