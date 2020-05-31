package com.hakan.marvel.data.model.comic

data class ComicsData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Comic>
)