package com.yourname.songtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourname.songtracker.ui.theme.SongTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = SongDatabase.getDatabase(applicationContext)
        val dao = database.songDao()

        setContent {
            SongTrackerTheme {
                val viewModel: SongViewModel = viewModel(
                    factory = SongViewModelFactory(dao)
                )

                SongTrackerUI(viewModel)
            }
        }
    }
}
