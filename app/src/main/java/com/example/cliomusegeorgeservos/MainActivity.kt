package com.example.cliomusegeorgeservos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    val myURL : String = "https://create.cliomuseapp.com/tour.json"

    //UI elements
    lateinit var posterImageView : ImageView
    lateinit var titleTextView : TextView
    lateinit var durationTextView : TextView
    lateinit var numOfLanguagesTextView: TextView
    lateinit var ratingTextView: TextView
    lateinit var numOfRatingsTextView: TextView
    lateinit var priceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize UI elements
        posterImageView = findViewById(R.id.poster_imageview)
        titleTextView = findViewById(R.id.title_textview)
        durationTextView = findViewById(R.id.duration_mins_textview)
        numOfLanguagesTextView = findViewById(R.id.num_of_languages_textview)
        ratingTextView = findViewById(R.id.rating_textview)
        numOfRatingsTextView = findViewById(R.id.num_of_ratings_textview)
        priceTextView = findViewById(R.id.price_textview)

        makeGetRequest()
    }

    fun makeGetRequest(){
        //get the json response in a big string
        myURL.httpGet().responseString { request, response, result ->
            when(result){
                is Result.Success -> { println("---Result: ${result.get()}")}
                is Result.Failure -> {}
            }
        }

        //get json response converted to gson and put in obects
        myURL.httpGet().responseObject(Tour.Deserializer()){request, response, result ->
            val (tour, err) = result
            println("---TOUR TITLE--- ${tour?.title}")
            println("---TOUR DURATION--- ${tour?.duration}")
            println("---TOUR RATING--- ${tour?.average_rating}")
            println("---TOUR RATING COUNT--- ${tour?.rating_count}")
            println("---TOUR PRICE--- ${tour?.retail_price}")
            runOnUiThread {
                updateUI(tour)
            }
        }
    }

    fun updateUI(tour : Tour?){
        //update ImageView with Picasso
        Picasso.get().load(tour?.thumbnail).into(posterImageView)
        //update title
        titleTextView.text = tour?.title
        //update duration
        durationTextView.text = tour?.duration + " minutes"
        //update rating
        ratingTextView.text = tour?.average_rating + "/5"
        //update num of ratings
        numOfRatingsTextView.text = "(${tour?.rating_count.toString()})"
        //update price
        priceTextView.text = tour?.retail_price + "$"


    }
}