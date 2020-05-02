package by.twitter.storage.entity

import androidx.room.*
import by.twitter.util.DateConverters
import java.time.OffsetDateTime

@Entity(tableName = "tweets", foreignKeys = [
    ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"])
])
class Tweet(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "created_at")
        @TypeConverters(DateConverters::class)
        val createdAt: OffsetDateTime,
        @ColumnInfo(name = "text")
        val text: String,
        @ColumnInfo(name = "retweet_count")
        val retweetCount: Int,
        @ColumnInfo(name = "retweeted")
        val retweeted: Boolean,
        @ColumnInfo(name = "favorite_count")
        val favoriteCount: Int,
        @ColumnInfo(name = "favorited")
        val favorited: Boolean,
        @ColumnInfo(name = "user_id")
        val userId: Long,
        @ColumnInfo(name = "quoted_status_id")
        val quotedStatusId: Long,
        @ColumnInfo(name = "is_quote_status")
        val isQuoteStatus: Boolean,
        @ColumnInfo(name = "in_reply_to_status_id")
        val inReplyToStatusId: Long
)