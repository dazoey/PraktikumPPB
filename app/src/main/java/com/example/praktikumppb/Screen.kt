package com.example.praktikumppb

sealed class Screen(val route: String, val title: String) {
    object Anime : Screen("anime", "Anime")
    object About : Screen("about", "About")
    object AnimeDetail : Screen("anime/{id}", "Anime Detail")
    object Characters : Screen("characters", "Characters")
    object CharacterDetail : Screen("character/{id}", "Character Detail")
}

