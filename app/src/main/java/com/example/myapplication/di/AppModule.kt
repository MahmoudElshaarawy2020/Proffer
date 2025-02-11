package com.example.myapplication.di

import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.RegisterRepoImpl
import com.example.myapplication.data.repository.VerificationRepoImpl
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.domain.repository.VerificationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRegisterRepo(apiService: ApiService): RegisterRepository {
        return RegisterRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideVerificationRepo(apiService: ApiService): VerificationRepo {
        return VerificationRepoImpl(apiService)
    }

}