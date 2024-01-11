package com.example.star_wars.di

import android.app.Application
import com.example.star_wars.database.PeopleDatabase
import com.example.star_wars.network.ApiInterface
import com.example.star_wars.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module for Dagger-Hilt Dependency Injection
 * @author by Harshil Gupta
 *
 * This class is used to define dependencies that can be used throughout the application
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Singleton
    @Provides
    fun getPeopleDatabase(application: Application): PeopleDatabase {
        return PeopleDatabase.getDatabase(application)
    }
}