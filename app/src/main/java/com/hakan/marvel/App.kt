package com.hakan.marvel

import android.annotation.SuppressLint
import android.app.Application
import com.hakan.marvel.app.di.component.DaggerAppComponent
import com.hakan.marvel.app.di.module.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.paperdb.Paper
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


@SuppressLint("Registered")
class App : Application() , HasAndroidInjector  {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        Paper.init(applicationContext)
        DaggerAppComponent.builder()
            .application(this)
            .networkModule(NetworkModule())
            .build()
            .inject(this)
    }

}
