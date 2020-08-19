package com.example.blackturtleassignment.handler

import com.example.blackturtleassignment.model.CategoryDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PixbayService {
    @GET("/api")
    fun getCategoryImages(
        @Query("key") key:String,
        @Query("category") category:String
    ):Single<CategoryDetails>
}