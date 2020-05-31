package com.hakan.marvel.domain

import androidx.lifecycle.LiveData
import com.hakan.marvel.data.datasource.DataSource
import com.hakan.marvel.data.model.character.CharactersResponse
import com.hakan.marvel.data.model.comic.ComicsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: DataSource) :Repository{
    override val isNetworkAvailable: LiveData<Boolean>
        get() = TODO("Not yet implemented")

    override fun getCharacters(page : Int): Flow<CharactersResponse>{
      return  dataSource.getCharacters(page)
    }

    override fun getComics(characterId: Int): Flow<ComicsResponse> {
        return  dataSource.getComics(characterId)
    }
}