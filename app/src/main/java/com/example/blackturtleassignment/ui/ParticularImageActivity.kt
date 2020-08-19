package com.example.blackturtleassignment.ui

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.blackturtleassignment.R
import com.example.blackturtleassignment.helper.DirectoryHelper
import com.example.blackturtleassignment.model.CategoryDetails
import com.example.blackturtleassignment.services.DownloadService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sdsmdg.tastytoast.TastyToast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_particular_image.*
import java.io.IOException
import java.lang.Exception

class ParticularImageActivity : AppCompatActivity() {
    private lateinit var hit: CategoryDetails.Hit
    private var imageLoaded =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particular_image)
        init()
    }

    private fun init() {
        //Getting the object of the Hit
        val modelJSON = intent.getStringExtra("model")
        val turnsType = object : TypeToken<CategoryDetails.Hit>() {}.type
        hit = Gson().fromJson<CategoryDetails.Hit>(modelJSON, turnsType)
        setUI()
        btnSave.setOnClickListener{
            if(imageLoaded) {
                saveWallpaper()
            }
            else
            {
                TastyToast.makeText(this@ParticularImageActivity,"Image Not yet loaded",TastyToast.LENGTH_SHORT,TastyToast.ERROR)
            }
        }
        btnDownload.setOnClickListener {
            dowmloadWallpaper()
        }
    }

    private fun dowmloadWallpaper() {
        Log.i("TAG", "dowmloadWallpaper: "+hit.largeImageURL)
        startService(DownloadService.getDownloadService(this, hit.largeImageURL, DirectoryHelper.ROOT_DIRECTORY_NAME.plus("/")))
    }

    private fun setUI() {
        Picasso.get().load(hit.largeImageURL).into(image,object :com.squareup.picasso.Callback{
            override fun onSuccess() {
                imageLoaded=true
            }

            override fun onError(e: Exception?) {
                imageLoaded=false
            }

        })
/*        Glide.with(image.context).load(hit.largeImageURL).listener(object : RequestListener<String,DrawableImageViewTarget>{

        }).into(image)*/
        user.text=hit.user
        tags.text=hit.tags
        tvLikes.text= hit.likes.toString()
        tvDownloads.text=hit.downloads.toString()
        tvViews.text=hit.views.toString()
        tvFavourite.text=hit.favorites.toString()
    }

    private fun saveWallpaper() {

        val manager=WallpaperManager.getInstance(applicationContext)
        try {
            manager.setBitmap((image.drawable as BitmapDrawable).bitmap)
            Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show();
        }
        catch (ex:IOException)
        {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

}