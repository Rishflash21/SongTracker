package com.yourname.songtracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SongTrackerUI(viewModel: SongViewModel) {
    var input by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }
    val songs by viewModel.songs.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(Color(0xFF121212))) {

        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("New Song") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = {
                viewModel.addSong(input)
                input = ""
            }) {
                Text("Add Song")
            }

            Spacer(Modifier.width(8.dp))

            TextField(
                value = search,
                onValueChange = {
                    search = it
                    viewModel.updateSearch(it)
                },
                label = { Text("Search") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(songs) { song ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(song.name, color = Color.White)
                    IconButton(onClick = { viewModel.deleteSong(song) }) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    }
                }
            }
        }
    }
}
