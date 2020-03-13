package by.twitter.model

import java.util.ArrayList

interface CRUD<E> {

    fun createTweet(element: E)

    fun readTweets(): ArrayList<E>

    fun updateTweet(element: E, index: Int)

    fun deleteTweet(index: Int)

}