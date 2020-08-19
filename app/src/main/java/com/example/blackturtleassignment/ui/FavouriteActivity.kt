package com.example.blackturtleassignment.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.blackturtleassignment.R
import com.example.blackturtleassignment.adapter.CategoryDetailsAdapter
import com.example.blackturtleassignment.database.AppDB
import com.example.blackturtleassignment.model.CategoryDetails
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import kotlinx.android.synthetic.main.activity_category_images.recyclerView
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class FavouriteActivity : AppCompatActivity(), CategoryDetailsAdapter.ImageClickCallback {
    lateinit var adapter:CategoryDetailsAdapter
    private lateinit var db: AppDB
    var data= arrayListOf<CategoryDetails.Hit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        toolbar.setTitle("BlackTurtle")
        db = Room.databaseBuilder(this@FavouriteActivity, AppDB::class.java, "BLACK_TURTLE_DB").build()
        recyclerView.layoutManager= LinearLayoutManager(this@FavouriteActivity)
        adapter= CategoryDetailsAdapter(data,this,"fav")
        recyclerView.adapter=adapter
       getItem()

    }

    private fun getItem() {
        data.clear()
        Thread {
            val list=db.favDao().readFav()
            data.addAll(list)
            runOnUiThread {
                // Stuff that updates the UI
                if(list.size>0) {
                    recyclerView.visibility = View.VISIBLE
                    no_image.visibility=View.GONE
                }
                else
                {
                    recyclerView.visibility=View.GONE
                    no_image.visibility=View.VISIBLE
                }

                adapter.notifyDataSetChanged()
            }
        }.start()
    }

    override fun onImageClickListener(imageDataCallBack: CategoryDetails.Hit?) {

    }

    override fun onAddFavClickListener(imageDataCallBack: CategoryDetails.Hit?) {

    }

    override fun onRemoveFavClickListener(imageDataCallBack: CategoryDetails.Hit?) {
        deleteDailog(imageDataCallBack)
    }
    private fun deleteDailog(imageDataCallBack: CategoryDetails.Hit?) {
        val alertDialog: LottieAlertDialog
        alertDialog =
            LottieAlertDialog.Builder(this@FavouriteActivity, DialogTypes.TYPE_CUSTOM, "alert.json")
                .setTitle("Delete")
                .setDescription("Are you sure you want remove from favouite..?")
                .setPositiveText("Ok")
                .setNegativeText("cancel")
                .setPositiveListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                       removeFav(imageDataCallBack)
                    }

                })
                .setNegativeListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }
                }).build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun removeFav(imageDataCallBack: CategoryDetails.Hit?) {
        Thread{
            imageDataCallBack!!.id?.let {
                db.favDao().deleteFav(it)
                getItem()
            }
        }.start()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //noinspection SimplifiableIfStatement
        if (item.itemId == R.id.action_home) {
            val intent=Intent(this@FavouriteActivity, HomeScreenActivity::class.java)
            startActivity(intent)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}