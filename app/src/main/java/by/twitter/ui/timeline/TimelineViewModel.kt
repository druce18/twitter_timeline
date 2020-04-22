package by.twitter.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.twitter.model.Tweet
import by.twitter.storage.TweetsRepository
import by.twitter.storage.TweetsRepositoryImpl
import javax.inject.Inject

class TimelineViewModel @Inject constructor(val tweetsRepository: TweetsRepository) : ViewModel() {

    private val tweetsData: MutableLiveData<List<Tweet>> by lazy {
        tweetsRepository.update()
    }


    fun getTweets(): LiveData<List<Tweet>> {
        return tweetsData
    }

}