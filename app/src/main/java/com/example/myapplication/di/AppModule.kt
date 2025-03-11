package com.example.myapplication.di

import android.app.Application
import android.content.Context
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.ContractorProfileRepoImpl
import com.example.myapplication.data.repository.CreateProjectRepoImpl
import com.example.myapplication.data.repository.HomeRepoImpl
import com.example.myapplication.data.repository.YourProfileRepoImpl
import com.example.myapplication.data.repository.LoginRepoImpl
import com.example.myapplication.data.repository.ProfileRepoImpl
import com.example.myapplication.data.repository.ProjectTypesRepoImpl
import com.example.myapplication.data.repository.RegisterRepoImpl
import com.example.myapplication.data.repository.VerificationRepoImpl
import com.example.myapplication.domain.repository.ContractorProfileRepository
import com.example.myapplication.domain.repository.CreateProjectRepository
import com.example.myapplication.domain.repository.HomeRepository
import com.example.myapplication.domain.repository.YourProfileRepository
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.ProfileRepository
import com.example.myapplication.domain.repository.ProjectTypesRepository
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.domain.repository.VerificationRepo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

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

    @Provides
    @Singleton
    fun provideHomeRepo(apiService: ApiService): HomeRepository {
        return HomeRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProjectTypesRepo(apiService: ApiService): ProjectTypesRepository {
        return ProjectTypesRepoImpl(apiService)
    }


    @Provides
    @Singleton
    fun provideCreateProjectRepo(apiService: ApiService): CreateProjectRepository {
        return CreateProjectRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }


    @Provides
    @Singleton
    fun provideContractorProfileRepo(apiService: ApiService): ContractorProfileRepository {
        return ContractorProfileRepoImpl(apiService)
    }
}