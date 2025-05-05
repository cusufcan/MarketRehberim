package com.cusufcan.marketrehberim.di

import com.cusufcan.marketrehberim.data.repository.MainRepositoryImpl
import com.cusufcan.marketrehberim.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainRepository(repositoryImpl: MainRepositoryImpl): MainRepository
}