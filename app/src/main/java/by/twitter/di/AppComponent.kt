package by.twitter.di

import android.content.Context
import by.twitter.ui.createtweet.CreateTweetFragment
import by.twitter.ui.main.MainActivity
import by.twitter.ui.main.MainFragment
import by.twitter.ui.timeline.TimelineFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, AppModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: TimelineFragment)

    fun inject(fragment: MainFragment)

    fun inject(fragment: CreateTweetFragment)

}
