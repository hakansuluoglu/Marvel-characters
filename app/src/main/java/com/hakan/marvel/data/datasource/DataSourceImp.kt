package com.hakan.marvel.data.datasource

import com.hakan.marvel.app.util.CHARACTER_LIMIT
import com.hakan.marvel.app.util.COMIC_LIMIT
import com.hakan.marvel.app.util.COMIC_ORDER_BY
import com.hakan.marvel.app.util.COMIC_START_YEAR
import com.hakan.marvel.data.api.Api
import com.hakan.marvel.data.model.character.CharactersResponse
import com.hakan.marvel.data.model.comic.ComicsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataSourceImp @Inject constructor(private val api: Api) : DataSource  {

    override fun getCharacters(page : Int): Flow<CharactersResponse> = flow{
        val characters = api.getCharacters(page * CHARACTER_LIMIT, CHARACTER_LIMIT)
        emit(characters)
    }

    override fun getComics(characterId: Int): Flow<ComicsResponse>  = flow{
        val comics = api.getComics(characterId, COMIC_START_YEAR, COMIC_LIMIT, COMIC_ORDER_BY)
        emit(comics)
    }
}