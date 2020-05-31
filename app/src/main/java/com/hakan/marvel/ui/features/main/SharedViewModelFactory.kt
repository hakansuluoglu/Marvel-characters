package com.hakan.marvel.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hakan.marvel.domain.Repository
import javax.inject.Inject

class SharedViewModelFactory @Inject constructor(private val repository: Repository ): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = SharedViewModel(
        repository
    ) as T
}