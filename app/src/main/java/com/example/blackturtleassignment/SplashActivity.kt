package com.example.blackturtleassignment

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.blackturtleassignment.ui.HomeScreenActivity
import com.example.blackturtleassignment.utils.Constants
import com.sdsmdg.tastytoast.TastyToast
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_category_images.*
import kotlinx.android.synthetic.main.activity_particular_image.*
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    private val mService by lazy { Constants.getAPI() }
    private val mDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mDisposable.add(
            mService.getCategoryImages("17950278-b06bc2d843c59d60c988305f1","backgrounds")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ photoList ->
                    if (photoList != null) {
                        photoList.hits?.let {
                           loadSplash(it.get(0).largeImageURL)
                        }

                    }
                }, {
                    if (this@SplashActivity != null) {
                        TastyToast.makeText(
                            this@SplashActivity,
                            "No Internet connection or server issue please try again after some time",
                            TastyToast.LENGTH_SHORT,
                            TastyToast.ERROR
                        )
                    }
                })
        )
    }

    private fun loadSplash(largeImageURL: String?) {
        //Splashscreen Loading
        Picasso.get().load(largeImageURL).into(splahscreen,object :com.squareup.picasso.Callback{
            override fun onSuccess() {
                val handler = Handler()
                handler.postDelayed(Runnable {

                    val intent = Intent(this@SplashActivity, HomeScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1000)
            }

            override fun onError(e: Exception?) {
                TastyToast.makeText(
                    this@SplashActivity,
                    "No Internet connection or server issue please try again after some time",
                    TastyToast.LENGTH_SHORT,
                    TastyToast.ERROR
                )
            }

        })
    }
}