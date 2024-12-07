package com.example.mockapiapplication.di

import com.example.mockapiapplication.data.core.data.module.NetworkModule
import com.example.mockapiapplication.data.datasource.remote.AppApi
import com.example.mockapiapplication.data.repository.AppRepositoryImpl
import com.example.mockapiapplication.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        appApi: AppApi,
    ): AppRepository {
        return AppRepositoryImpl(appApi)
    }
}