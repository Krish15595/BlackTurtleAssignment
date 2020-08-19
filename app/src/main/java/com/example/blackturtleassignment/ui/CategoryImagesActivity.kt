package com.example.blackturtleassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.blackturtleassignment.R
import com.example.blackturtleassignment.adapter.CategoryDetailsAdapter
import com.example.blackturtleassignment.database.AppDB
import com.example.blackturtleassignment.model.CategoryDetails
import com.example.blackturtleassignment.utils.Constants
import com.google.gson.Gson
import com.sdsmdg.tastytoast.TastyToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_category_images.*

class CategoryImagesActivity : AppCompatActivity(), CategoryDetailsAdapter.ImageClickCallback {
    private val mService by lazy { Constants.getAPI() }
    private val mDisposable = CompositeDisposable()
    lateinit var adapter:CategoryDetailsAdapter
    private lateinit var db: AppDB
    var data= arrayListOf<CategoryDetails.Hit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_images)
        init()
    }

    private fun init() {
        db = Room.databaseBuilder(this@CategoryImagesActivity, AppDB::class.java, "BLACK_TURTLE_DB").build()
        recyclerView.layoutManager= LinearLayoutManager(this@CategoryImagesActivity)
        adapter= CategoryDetailsAdapter(data,this,"dashboard")
        recyclerView.adapter=adapter
        if(intent!=null) {
            val categoryName=intent.getStringExtra("categoryName")
            mDisposable.add(
                mService.getCategoryImages("17950278-b06bc2d843c59d60c988305f1",categoryName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ photoList ->
                        progressBar.visibility = View.GONE
                        recyclerView.visibility=View.VISIBLE
                        if (photoList != null) {
                            photoList.hits?.let { data.addAll(it) }
                            adapter.notifyDataSetChanged()

                        }
                    }, {
                        if (this@CategoryImagesActivity != null) {
//                        requireContext().ProgressbarStop()
                            TastyToast.makeText(
                                this@CategoryImagesActivity,
                                "No Internet connection or server issue please try again after some time",
                                TastyToast.LENGTH_SHORT,
                                TastyToast.ERROR
                            )
                        }
                    })
            )
        }
    }

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    override fun onImageClickListener(imageDataCallBack: CategoryDetails.Hit?) {
        val jsonModel = imageDataCallBack?.let { Gson().toJsonTree(it).asJsonObject }
        val intent = Intent(this@CategoryImagesActivity, ParticularImageActivity::class.java)
        intent.putExtra("model",jsonModel.toString())
        startActivity(intent)

    }

    override fun onAddFavClickListener(imageDataCallBack: CategoryDetails.Hit?) {
        Thread {
            val id = db.favDao().checkifExist(imageDataCallBack!!.id!!)
            if (id < 1) {
                val id = db.favDao().savFav(imageDataCallBack)
                if (id > 0) {
                    val list=db.favDao().readFav()

                    showToast("Combination added to favourite list","success")
                } else {
                    showToast("Unable to add in fav list","error")
                }
            } else {
                showToast("This combination is already added is fav list","error")
            }
        }.start()
    }

    override fun onRemoveFavClickListener(imageDataCallBack: CategoryDetails.Hit?) {

    }

    fun showToast(toast: String?,type:String) {
        runOnUiThread {
            if(type=="error") {
                TastyToast.makeText(
                    this@CategoryImagesActivity,
                    toast,
                    TastyToast.LENGTH_SHORT,
                    TastyToast.ERROR
                )
            }
            else
            {
                TastyToast.makeText(
                    this@CategoryImagesActivity,
                    toast,
                    TastyToast.LENGTH_SHORT,
                    TastyToast.SUCCESS
                )
            }
        }
    }
}