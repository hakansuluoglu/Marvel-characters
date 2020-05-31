package com.hakan.marvel.app.di.module

import android.app.Application
import android.content.Context
import com.hakan.marvel.App
import com.hakan.marvel.data.api.Api
import com.hakan.marvel.data.datasource.DataSource
import com.hakan.marvel.data.datasource.DataSourceImp
import com.hakan.marvel.domain.Repository
import com.hakan.marvel.domain.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    var mApplication = App()

    @Provides
    @Singleton
    fun providesApplication(): Application = mApplication

    @Provides
    @Singleton
    fun providesContext(): Context = mApplication

    @Provides
    @Singleton
    internal fun provideDataSource(api: Api): DataSource = DataSourceImp(api)

    @Provides
    @Singleton
    internal fun provideRepository(dataSource: DataSource): Repository = RepositoryImpl(dataSource)

}