package by.twitter.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.twitter.ui.createtweet.CreateTweetViewModel
import by.twitter.ui.profile.UserProfileViewModel
import by.twitter.ui.timeline.TimelineViewModel
import by.twitter.ui.profile.TweetProfileViewModel
import by.twitter.ui.timeline.RetweetLikeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TimelineViewModel::class)
    internal abstract fun timelineViewModel(viewModel: TimelineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateTweetViewModel::class)
    internal abstract fun createTweetViewModel(viewModel: CreateTweetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    internal abstract fun userProfileViewModel(viewModel: UserProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TweetProfileViewModel::class)
    internal abstract fun tweetProfileViewModel(viewModel: TweetProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RetweetLikeViewModel::class)
    internal abstract fun retweetLikeViewModel(viewModel: RetweetLikeViewModel): ViewModel

}