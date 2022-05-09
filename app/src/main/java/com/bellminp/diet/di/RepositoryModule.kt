package com.bellminp.diet.di

import com.bellminp.diet.data.data_source.local.DietDataLocalDataSource
import com.bellminp.diet.data.data_source.local.ProfileLocalDataSource
import com.bellminp.diet.data.repository.DietDataRepositoryImpl
import com.bellminp.diet.data.repository.ProfileRepositoryImpl
import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDietDataRepository(
        dataSource : DietDataLocalDataSource
    ) : DietDataRepository {
        return DietDataRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        dataSource : ProfileLocalDataSource
    ) : ProfileRepository {
        return ProfileRepositoryImpl(dataSource)
    }
}