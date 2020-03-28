package by.twitter.ui.http

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.twitter.R
import kotlinx.android.synthetic.main.fragment_http.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.Executors

class LearnHttpFragment : Fragment(R.layout.fragment_http) {

    private val executorService = Executors.newFixedThreadPool(1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startRequestButton.setOnClickListener {
            makeGetRequest()
        }
    }

    private fun makeGetRequest() {
        executorService.submit {
            val url = URL("http://httpbin.org/ip")
            val connection = url.openConnection()
            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
            var inputLine: String?
            while (reader.readLine().also { inputLine = it } != null) {
                println(inputLine)
            }
            reader.close()
        }
    }

    companion object {

        fun newInstance(): Fragment = LearnHttpFragment()

    }
}