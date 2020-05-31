package com.hakan.marvel.app.di.module

import com.hakan.marvel.ui.features.main.character_detail.CharacterDetailFragment
import com.hakan.marvel.ui.features.main.characters.CharactersFragment
import com.hakan.marvel.ui.features.main.favorite_characters.BookmarkFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeCharactersFragment(): CharactersFragment

    @ContributesAndroidInjector
    abstract fun contributeCharacterDetailFragment():CharacterDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeBookmarkFragment(): BookmarkFragment
}