package by.twitter.util

import by.twitter.network.model.TweetPayload
import by.twitter.network.model.UserPayload
import by.twitter.storage.entity.Tweet
import by.twitter.storage.entity.User

object Mapper {

    fun userPayloadToEntity(userPayload: UserPayload): User {
        val user = User(
                id = userPayload.id,
                name = userPayload.name,
                screenName = userPayload.screenName,
                location = userPayload.location,
                urlUser = userPayload.urlUser,
                description = userPayload.description,
                createdAt = userPayload.createdAt,
                friendsCount = userPayload.friendsCount,
                followersCount = userPayload.followersCount,
                profileImageUrlHttps = userPayload.profileImageUrlHttps,
                profileBannerUrl = userPayload.profileBannerUrl
        )
        return user
    }

    fun tweetPayloadToEntity(tweetPayload: TweetPayload): Tweet {
        val tweet = Tweet(
                id = tweetPayload.id,
                createdAt = DateUtil.getOffsetDateTime(tweetPayload.createdAt),
                text = tweetPayload.text,
                retweetCount = tweetPayload.retweetCount,
                retweeted = tweetPayload.retweeted,
                favoriteCount = tweetPayload.favoriteCount,
                favorited = tweetPayload.favorited,
                userId = tweetPayload.user.id,
                quotedStatusId = tweetPayload.quotedStatusId,
                isQuoteStatus = tweetPayload.isQuoteStatus,
                inReplyToStatusId = tweetPayload.inReplyToStatusId
        )
        return tweet
    }
}