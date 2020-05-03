package by.twitter.di

import by.twitter.storage.TweetRepository
import by.twitter.storage.TweetRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(tweetRepository: TweetRepositoryImpl): TweetRepository


}