package com.arrudeia.core.data.di

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.data.network.ProfileRepositoryImpl
import com.arrudeia.core.data.repository.ProfileRepository
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
    fun provideCountryClient(apolloClient: ApolloClient): ProfileRepository {
        return ProfileRepositoryImpl(apolloClient)
    }

}