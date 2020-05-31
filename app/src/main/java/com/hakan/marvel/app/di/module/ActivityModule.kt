package com.hakan.marvel.app.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hakan.marvel.app.di.ViewModelKey
import com.hakan.marvel.ui.features.main.MainActivity
import com.hakan.marvel.ui.features.main.SharedViewModel
import com.hakan.marvel.ui.features.main.SharedViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    abstract fun bindSharedViewModelFactory(sharedViewModelFactory: SharedViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    protected abstract fun bindSharedViewModel(sharedViewModel: SharedViewModel): ViewModel

}