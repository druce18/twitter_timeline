package by.twitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContent, NewMainFragment.newInstance())
                .commit()
        }
    }
}
