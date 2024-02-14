package com.arrudeia.core.network.retrofit

import com.arrudeia.core.network.BuildConfig
import com.arrudeia.core.network.retrofit.service.RetrofitArrudeiaNetworkService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


private const val ARRUDEIA_BASE_URL = BuildConfig.BACKEND_URL
private const val ALL_TRAVEL = BuildConfig.ALL_TRAVEL
private const val TYPE_DRIVE_EXPORT = BuildConfig.TYPE_DRIVE_EXPORT

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(httpLoggingInterceptor)

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder().baseUrl(ARRUDEIA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpBuilder.build())
            .build()
    }

    @Singleton
    @Provides
    fun providesArrudeiaApi(retrofit: Retrofit): RetrofitArrudeiaNetworkService {
        return retrofit.create(RetrofitArrudeiaNetworkService::class.java)
    }
}