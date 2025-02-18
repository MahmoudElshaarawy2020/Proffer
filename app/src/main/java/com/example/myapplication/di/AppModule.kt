package com.example.myapplication.di

import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.YourProfileRepoImpl
import com.example.myapplication.data.repository.LoginRepoImpl
import com.example.myapplication.data.repository.ProfileRepoImpl
import com.example.myapplication.data.repository.RegisterRepoImpl
import com.example.myapplication.data.repository.VerificationRepoImpl
import com.example.myapplication.domain.repository.YourProfileRepository
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.ProfileRepository
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

    @Provides
    @Singleton
    fun provideLoginRepo(apiService: ApiService): LoginRepository {
        return LoginRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProfileRepo(apiService: ApiService): ProfileRepository {
        return ProfileRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun yourProfileRepo(apiService: ApiService): YourProfileRepository {
        return YourProfileRepoImpl(apiService)
    }

}