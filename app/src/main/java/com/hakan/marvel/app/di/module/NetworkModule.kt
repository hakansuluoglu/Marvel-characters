package com.hakan.marvel.app.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hakan.marvel.app.util.BASE_URL
import com.hakan.marvel.app.util.PRIVATE_API_KEY
import com.hakan.marvel.app.util.PUBLIC_API_KEY
import com.hakan.marvel.app.util.md5
import com.hakan.marvel.data.api.Api
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gson: Gson
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        gson = gsonBuilder.create()
        GsonConverterFactory.create(gson)
        return gson
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC+3")).timeInMillis / 1000L).toString()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", PUBLIC_API_KEY)
                .addQueryParameter("ts", ts)
                .addQueryParameter("hash", "$ts$PRIVATE_API_KEY$PUBLIC_API_KEY".md5())
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


}