package by.twitter.di

import android.content.Context
import by.twitter.ui.main.MainActivity
import by.twitter.ui.main.MainFragment
import by.twitter.ui.timeline.TimelineViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [StorageModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: TimelineViewModel)

    fun inject(activity: MainActivity)

    fun inject(activity: MainFragment)

}
