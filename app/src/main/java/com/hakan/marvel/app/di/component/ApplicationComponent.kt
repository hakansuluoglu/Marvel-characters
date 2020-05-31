package com.hakan.marvel.app.di.component

import com.hakan.marvel.App
import com.hakan.marvel.app.di.module.ActivityModule
import com.hakan.marvel.app.di.module.AppModule
import com.hakan.marvel.app.di.module.FragmentModule
import com.hakan.marvel.app.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        NetworkModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        @BindsInstance
        fun networkModule(networkModule: NetworkModule): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)

}