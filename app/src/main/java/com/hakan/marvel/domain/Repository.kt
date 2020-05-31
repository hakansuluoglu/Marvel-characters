package com.hakan.marvel.domain

import androidx.lifecycle.LiveData
import com.hakan.marvel.data.model.character.CharactersResponse
import com.hakan.marvel.data.model.comic.ComicsResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    val isNetworkAvailable: LiveData<Boolean>
    fun getCharacters(page : Int) : Flow<CharactersResponse>
    fun getComics(characterId: Int) : Flow<ComicsResponse>
}