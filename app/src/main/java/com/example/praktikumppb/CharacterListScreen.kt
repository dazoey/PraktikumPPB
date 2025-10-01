package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.praktikumppb.viewmodel.CharacterViewModel

@Composable
fun CharacterListScreen(navController: NavController, viewModel: CharacterViewModel = viewModel()) {
    val characterList by viewModel.characterList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchTopCharacters()
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.searchCharacter(it)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Button(onClick = { viewModel.sortCharacter(true) }) {
                Text("Sort A-Z")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.sortCharacter(false) }) {
                Text("Sort Z-A")
            }
        }
        LazyColumn {
            items(characterList) { character ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("character/${character.mal_id}") }
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(character.images.jpg.image_url),
                            contentDescription = character.name,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(character.name)
                    }
                }
            }
        }
    }
}
