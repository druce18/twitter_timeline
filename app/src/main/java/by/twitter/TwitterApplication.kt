package by.twitter

import android.app.Application
import by.twitter.di.AppComponent
import by.twitter.di.DaggerAppComponent

class TwitterApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}
