package com.example.retrofitexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.retrofitexample.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUri = "https://jsonplaceholder.typicode.com/"

    lateinit var mainBinding: ActivityMainBinding

    var postsList = ArrayList<Posts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root

        setContentView(view)

        showPosts()

    }

    private fun showPosts() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUri)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)

        val call: Call<List<Posts>> = retrofitAPI.getAllPosts()

        call.enqueue(object : Callback<List<Posts>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {

                if (!response.isSuccessful) {

                    mainBinding.textViewUserId.text = "error"
                    mainBinding.textViewId.text = "error"
                    mainBinding.textViewTitle.text = "error"
                    mainBinding.textViewBody.text = "error"

                }

                postsList = response.body() as ArrayList<Posts>
                mainBinding.textViewUserId.text = postsList[0].userId.toString()
                mainBinding.textViewId.text = postsList[0].userId.toString()
                mainBinding.textViewTitle.text = postsList[0].title
                mainBinding.textViewBody.text = postsList[0].subtitle

            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {

                Toast.makeText(
                    applicationContext,
                    t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()

            }

        })

    }
}