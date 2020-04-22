package by.twitter.di

import by.twitter.storage.TweetsRepository
import by.twitter.storage.TweetsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(storage: TweetsRepositoryImpl): TweetsRepository

}