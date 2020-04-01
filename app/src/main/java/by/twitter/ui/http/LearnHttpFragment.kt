package by.twitter.ui.http

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.twitter.R
import kotlinx.android.synthetic.main.fragment_http.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLConnection
import java.net.URLEncoder
import java.util.concurrent.Executors

class LearnHttpFragment : Fragment(R.layout.fragment_http) {

    private val executorService = Executors.newFixedThreadPool(1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startGetRequestButton.setOnClickListener {
            makeGetRequest()
        }

        startPostRequestButton.setOnClickListener {
            makePostRequest()
        }
    }

    private fun makeGetRequest() {
        executorService.submit {
            val url = URL("https://httpbin.org/ip")
            val connection = url.openConnection()
            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
            var inputLine: String?

            val response = mutableListOf<String>()
            response.add("GET response")

            while (reader.readLine().also { inputLine = it } != null) {
                println(inputLine)
                inputLine?.let { response.add(it) }
            }
            reader.close()
            httpTextView.text = response.joinToString(separator = "\n")
        }
    }

    private fun makePostRequest() {
        executorService.submit {
            val stringToReverse: String = URLEncoder.encode("test_post", "UTF-8")

            val url = URL("https://httpbin.org/response-headers")
            val connection: URLConnection = url.openConnection()
            connection.doOutput = true

            val out = OutputStreamWriter(connection.getOutputStream())
            out.write("freeform=$stringToReverse")
            out.close()

            val response = mutableListOf<String>()
            response.add("POST response")

            val input = BufferedReader(InputStreamReader(connection.getInputStream()))
            var decodedString: String?
            while (input.readLine().also { decodedString = it } != null) {
                println(decodedString)
                decodedString.let { it?.let { it1 -> response.add(it1) } }
            }
            input.close()
            httpTextView.text = response.joinToString(separator = "\n")
        }
    }

    companion object {

        fun newInstance(): Fragment = LearnHttpFragment()

    }
}