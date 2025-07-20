package com.yourname.songtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SongViewModel(private val dao: SongDao) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val songs: StateFlow<List<Song>> = searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                dao.getAllSongs()
            } else {
                dao.searchSongs(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addSong(name: String) {
        viewModelScope.launch {
            if (name.isNotBlank()) {
                dao.insertSong(Song(name = name.trim()))
            }
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            dao.deleteSong(song)
        }
    }
}

class SongViewModelFactory(private val dao: SongDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
