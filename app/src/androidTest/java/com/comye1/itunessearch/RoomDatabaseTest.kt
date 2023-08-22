package com.comye1.itunessearch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.comye1.itunessearch.data.local.FavoriteTrack
import com.comye1.itunessearch.data.local.FavoritesDao
import com.comye1.itunessearch.data.local.FavoritesDatabase
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var dao: FavoritesDao
    private lateinit var db: FavoritesDatabase

    private val testScope = TestScope()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FavoritesDatabase::class.java
        ).build()
        dao = db.favoritesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeTrackAndReadInList() {
        val track = FavoriteTrack(
            id = 1L,
            artworkUrl = "artwork",
            trackName = "track",
            artistName = "artist",
            collectionName = "collection"
        )
        testScope.runTest {
            dao.insert(track)
            var byId = dao.findTrack(track.id)
            assertThat(byId, equalTo(track))
            dao.delete(track)
            byId = dao.findTrack(track.id)
            assertThat(byId, equalTo(null))
        }
    }
}