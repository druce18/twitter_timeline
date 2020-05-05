package by.twitter.di

import by.twitter.TwitterApplication
import by.twitter.ui.create.CreateTweetFragment
import by.twitter.ui.main.MainActivity
import by.twitter.ui.main.MainFragment
import by.twitter.ui.timeline.TimelineFragment
import by.twitter.ui.profile.UserProfileFragment
import by.twitter.ui.profile.TweetProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, AppModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: TwitterApplication): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: TimelineFragment)

    fun inject(fragment: MainFragment)

    fun inject(fragment: CreateTweetFragment)

    fun inject(fragment: UserProfileFragment)

    fun inject(fragment: TweetProfileFragment)

}
