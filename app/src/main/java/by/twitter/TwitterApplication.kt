package by.twitter

import android.app.Application
import by.twitter.di.AppComponent
import by.twitter.di.DaggerAppComponent
import timber.log.Timber

class TwitterApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        }
    }
}
