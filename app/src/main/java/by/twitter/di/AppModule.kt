package by.twitter.di

import by.twitter.network.TwitterConnection
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideTwitterService() = TwitterConnection()

}