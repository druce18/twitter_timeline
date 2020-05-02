package by.twitter.di

import android.content.Context
import androidx.room.Room
import by.twitter.storage.AppDatabase
import by.twitter.storage.TweetRepository
import by.twitter.storage.TweetRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(tweetRepository: TweetRepositoryImpl): TweetRepository


    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideDb(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME_DB).build()
        }

    }
}