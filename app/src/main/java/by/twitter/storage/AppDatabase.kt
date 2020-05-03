package by.twitter.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.User
import by.twitter.util.DateConverters


@Database(entities = [Tweet::class, User::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tweetDao(): TweetDao

    companion object {
        const val NAME_DB = "APP_DB"
    }
}