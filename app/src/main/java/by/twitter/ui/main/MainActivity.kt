package by.twitter.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.twitter.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContent,
                    MainFragment.newInstance()
                )
                .commit()
        }
    }
}
