package com.hakan.marvel.data.api

import com.hakan.marvel.app.util.CHARACTERS_ENDPOINT
import com.hakan.marvel.app.util.COMICS_ENDPOINT
import com.hakan.marvel.data.model.character.CharactersResponse
import com.hakan.marvel.data.model.comic.ComicsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(value = CHARACTERS_ENDPOINT)
    suspend fun getCharacters(@Query("offset") offset: Int,
                              @Query("limit") limit: Int): CharactersResponse

    @GET(value = COMICS_ENDPOINT)
    suspend fun getComics(@Path("character_id") character_id:  Int,
                          @Query("startYear") startYear: Int,
                          @Query("limit") limit: Int,
                          @Query("orderBy") orderBy: String): ComicsResponse

}