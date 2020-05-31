package com.hakan.marvel.data.model.character

import com.hakan.marvel.data.model.Thumbnail
import com.hakan.marvel.data.model.comic.Comic

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    var savedComics: List<Comic>?
)