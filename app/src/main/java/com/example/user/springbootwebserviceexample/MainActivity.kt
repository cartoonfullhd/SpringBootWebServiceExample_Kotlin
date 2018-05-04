package com.example.user.springbootwebserviceexample

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getBook(id, nameBook, author).execute()
    }

    class getBook(idTextView: TextView, nameTextView: TextView, authorTextView: TextView) : AsyncTask<Unit, Unit, String>()
    {
        val idTextView: TextView? = idTextView
        val nameTextView: TextView? = nameTextView
        val authorTextview: TextView? = authorTextView

        override fun doInBackground(vararg params: Unit?): String? {
            val url = "http://192.168.3.108:8080/books"
            val obj = URL(url)
            var message = ""

            with(obj.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = "GET"


                println("\nSending 'GET' request to URL : $url")
                println("Response Code : $responseCode")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    //println(response.toString()+"d5ddd")
                    Log.d("myTag", response.toString()+"d5ddd")
                    message = response.toString()
                }

            }
            return message
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val cartoonBook = JSONArray(result).getString(0)
            val historyBook = JSONArray(result).getString(1)
            idTextView?.text = JSONObject(cartoonBook).getString("id")
            nameTextView?.text = JSONObject(cartoonBook).getString("name")
            authorTextview?.text = JSONObject(cartoonBook).getString("author")

        }
    }
}
