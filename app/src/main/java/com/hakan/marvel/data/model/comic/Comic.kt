package com.hakan.marvel.data.model.comic

import com.hakan.marvel.data.model.Thumbnail

data class Comic (
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Thumbnail
)