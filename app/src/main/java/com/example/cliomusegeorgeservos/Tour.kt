package com.example.cliomusegeorgeservos

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.GsonBuilder

data class Tour(var sku: String,
                var title: String,
                var header_image: String,
                var thumbnail: String,
                var permalink: String,
                var average_rating: String,
                var rating_count: Int,
                var retail_price: String,
                var sales_price: String,
                var duration: String) {

    class Deserializer: ResponseDeserializable<Tour>{
        override fun deserialize(content: String): Tour? = Gson().fromJson(content, Tour::class.java)
    }
}