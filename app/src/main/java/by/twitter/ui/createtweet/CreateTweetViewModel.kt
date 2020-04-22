package by.twitter.ui.createtweet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.twitter.storage.TweetsRepository
import javax.inject.Inject

class CreateTweetViewModel @Inject constructor(private val tweetsRepository: TweetsRepository) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _goBackToTimeline = MutableLiveData(false)
    val goBackToTimeline: LiveData<Boolean> = _goBackToTimeline

    private var requestEnd = MutableLiveData(false)

    private val observerRequest = Observer<Boolean> { t ->
        if (t!!) {
            _loading.value = false
            _goBackToTimeline.value = true
        } else {
            _loading.value = true
            _goBackToTimeline.value = false
        }
    }


    fun createTweet(text: String) {
        requestEnd = tweetsRepository.create(text)
        requestEnd.observeForever(observerRequest)
    }

    override fun onCleared() {
        super.onCleared()
        requestEnd.removeObserver(observerRequest)
    }

}