
package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.praktikumppb.viewmodel.AnimeViewModel

@Composable
fun AnimeDetailScreen(animeId: Int, viewModel: AnimeViewModel = viewModel()) {
    val anime by viewModel.anime.collectAsState()

    LaunchedEffect(animeId) {
        viewModel.fetchAnimeById(animeId)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (anime == null) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(anime!!.images.jpg.image_url),
                    contentDescription = anime!!.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = anime!!.title, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Type: ${anime!!.type ?: "-"}")
                Text(text = "Episodes: ${anime!!.episodes ?: 0}")
                Text(text = "Score: ${anime!!.score ?: "N/A"}")
            }
        }
    }
}
