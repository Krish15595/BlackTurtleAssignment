package com.example.blackturtleassignment.utils

import com.appvantis.perkserp.helper.RetrofitClient
import com.example.blackturtleassignment.handler.PixbayService


//import com.google.firebase.iid.FirebaseInstanceId

class Constants {

    companion object {
      const val BASE_URL = "https://pixabay.com/api/" //uat

        const val PHOTO_IMAGE_URL=""

        fun getAPI(): PixbayService = RetrofitClient.getClient(BASE_URL)!!.create(PixbayService::class.java)

    }
}
