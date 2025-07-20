package com.yourname.songtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SongViewModel(private val dao: SongDao) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _songs = _searchQuery.flatMapLatest { query ->
        if (query.isEmpty()) dao.getAll() else dao.search(query)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val songs: StateFlow<List<Song>> = _songs

    fun addSong(name: String) {
        viewModelScope.launch {
            if (name.isNotBlank()) dao.insert(Song(name = name.trim()))
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch { dao.delete(song) }
    }

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }
}
