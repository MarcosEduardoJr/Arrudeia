package com.arrudeia.feature.profile.di

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GraphQlModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://arrudeiaapi-paft5trccq-uc.a.run.app/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryClient(apolloClient: ApolloClient): com.arrudeia.feature.profile.data.ProfileRepository {
        return ProfileRepositoryImpl(apolloClient)
    }

}