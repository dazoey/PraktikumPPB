package com.example.praktikumppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// TODO: Replace with actual data
data class Member(
    val name: String,
    val nim: String,
    val group: String,
    val shift: String,
    val photo: Int
)

@Composable
fun AboutScreen() {
    // TODO: Replace with actual data and add your own drawable resources
    val members = listOf(
        Member("Muhammad Rifky Athaya", "21120123140129", "Kelompok 10", "2", androidx.core.R.drawable.ic_call_answer),
        Member("Muhamamad Fakhri Akmal", "21120123140172", "Kelompok 10", "2", androidx.core.R.drawable.ic_call_answer),
        Member("fadhlqan yuqa T", "21120123140147", "Kelompok 10", "2", androidx.core.R.drawable.ic_call_answer),
        Member("Razzaq Permana", "21120123120016", "Kelompok 10", "2", androidx.core.R.drawable.ic_call_answer)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(members) { member ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = member.photo),
                        contentDescription = member.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(member.name, style = MaterialTheme.typography.headlineSmall)
                        Text("NIM: ${member.nim}")
                        Text("Group: ${member.group}")
                        Text("Shift: ${member.shift}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    AboutScreen()
}
