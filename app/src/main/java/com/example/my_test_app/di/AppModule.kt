package com.example.my_test_app.di

import com.example.my_test_app.data.AppRepositoryImpl
import com.example.my_test_app.domain.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindAppRepository(
        impl: AppRepositoryImpl
    ): AppRepository
}