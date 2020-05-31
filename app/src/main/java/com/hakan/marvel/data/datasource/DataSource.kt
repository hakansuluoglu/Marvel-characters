package com.hakan.marvel.data.datasource

import com.hakan.marvel.data.model.character.CharactersResponse
import com.hakan.marvel.data.model.comic.ComicsResponse
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getCharacters(page : Int) : Flow<CharactersResponse>
    fun getComics(characterId: Int) : Flow<ComicsResponse>
}