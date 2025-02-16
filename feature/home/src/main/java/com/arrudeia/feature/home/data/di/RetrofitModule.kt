package com.arrudeia.feature.home.data.di

import com.arrudeia.feature.home.data.retrofit.GoogleEventApiService
import com.arrudeia.feature.home.data.retrofit.HotelApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://serpapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHotelApiService(retrofit: Retrofit): HotelApiService {
        return retrofit.create(HotelApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventApiService(retrofit: Retrofit): GoogleEventApiService {
        return retrofit.create(GoogleEventApiService::class.java)
    }
}