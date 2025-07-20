package com.yourname.songtracker

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: Song)

    @Delete
    suspend fun delete(song: Song)

    @Query("SELECT * FROM Song ORDER BY id DESC")
    fun getAll(): Flow<List<Song>>

    @Query("SELECT * FROM Song WHERE name LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<Song>>
}
