package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.praktikumppb.viewmodel.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(viewModel: AnimeViewModel = viewModel(), navController: NavController) {
    val animeList by viewModel.animeList.collectAsState()
    val genres by viewModel.genres.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf<com.example.praktikumppb.model.Genre?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchTopAnime()
        viewModel.fetchAnimeGenres()
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.searchAnime(it)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Button(onClick = { viewModel.sortAnime(true) }) {
                Text("Sort A-Z")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.sortAnime(false) }) {
                Text("Sort Z-A")
            }
        }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedGenre?.name ?: "Select Genre",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genres.forEach { genre ->
                    DropdownMenuItem(
                        text = { Text(genre.name) },
                        onClick = { 
                            selectedGenre = genre
                            viewModel.fetchTopAnime(genre.mal_id)
                            expanded = false
                        }
                    )
                }
            }
        }
        LazyColumn {
            items(animeList) { anime ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("anime/${anime.mal_id}") }
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(anime.images.jpg.image_url),
                            contentDescription = anime.title,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(anime.title)
                            Text("Type: ${anime.type ?: "-"}")
                            Text("Episodes: ${anime.episodes ?: 0}")
                            Text("Score: ${anime.score ?: "N/A"}")
                        }
                    }
                }
            }
        }
    }
}
