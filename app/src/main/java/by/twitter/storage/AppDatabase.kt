package by.twitter.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.User


@Database(entities = [Tweet::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tweetDao(): TweetDao

    companion object {
        const val NAME_DB = "my_db"
    }
}