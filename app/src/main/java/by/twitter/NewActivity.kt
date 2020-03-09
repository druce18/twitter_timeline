package by.twitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContent, NewFragment.newInstance())
                .commit()
        }
    }
}
