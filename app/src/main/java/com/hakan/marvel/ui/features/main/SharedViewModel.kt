package com.hakan.marvel.ui.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakan.marvel.data.model.character.Character
import com.hakan.marvel.data.model.comic.Comic
import com.hakan.marvel.domain.Repository
import com.hakan.marvel.domain.Resource
import io.paperdb.Paper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val mutableCharactersLiveData = MutableLiveData<Resource<List<Character>>>()
    val mutableComicsLiveData = MutableLiveData<Resource<List<Comic>>>()
    val mutableLocalCharacterLiveData = MutableLiveData<List<Character>>()
    val mutableSelectedCharacter = MutableLiveData<Character>()
    private var characterTotal : Int = 0
    private var characterCount : Int = 0

    fun  getCharacters(page : Int) = viewModelScope.launch{
        if( page == 0 || characterTotal > characterCount ) {
            repository.getCharacters(page)
                .onStart { mutableCharactersLiveData.value = Resource.loading() }
                .catch { mutableCharactersLiveData.value = Resource.error(it.localizedMessage, 0) }
                .collect {
                    mutableCharactersLiveData.value = Resource.success(it.data.results)
                    characterCount += it.data.count
                    if (page == 0) characterTotal = it.data.total
                }
        }
    }

    fun getComics(characterId: Int) = viewModelScope.launch{
        repository.getComics(characterId)
            .onStart { mutableComicsLiveData.value = Resource.loading() }
            .catch   { mutableComicsLiveData.value = Resource.error (it.localizedMessage,0)}
            .collect {
                mutableComicsLiveData.value = Resource.success(it.data.results)
            }
    }

    fun getLocalCharacters() = viewModelScope.launch{
         mutableLocalCharacterLiveData.value = Paper.book().read("favorite", java.util.ArrayList<Character>())
    }

}
