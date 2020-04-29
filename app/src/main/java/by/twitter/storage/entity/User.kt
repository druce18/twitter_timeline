package by.twitter.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "screenName")
        val screenName: String,
        @ColumnInfo(name = "location")
        val location: String,
        @ColumnInfo(name = "urlUser")
        val urlUser: String,
        @ColumnInfo(name = "description")
        val description: String,
        @ColumnInfo(name = "createdAt")
        val createdAt: String,
        @ColumnInfo(name = "friendsCount")
        val friendsCount: Int,
        @ColumnInfo(name = "followersCount")
        val followersCount: Int,
        @ColumnInfo(name = "profileImageUrlHttps")
        val profileImageUrlHttps: String,
        @ColumnInfo(name = "profileBannerUrl")
        val profileBannerUrl: String
)