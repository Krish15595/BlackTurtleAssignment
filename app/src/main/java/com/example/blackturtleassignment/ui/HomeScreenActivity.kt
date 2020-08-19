package com.example.blackturtleassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.blackturtleassignment.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    fun init(){
        setSupportActionBar(toolbar)
        toolbar.setTitle("BlackTurtle")
        backgrounds.setOnClickListener{
            categoryImage(backgrounds.text.toString().toLowerCase())
        }
        fashion.setOnClickListener{
            categoryImage(fashion.text.toString().toLowerCase())
        }
        nature.setOnClickListener{
            categoryImage(nature.text.toString().toLowerCase())
        }
        science.setOnClickListener{
            categoryImage(science.text.toString().toLowerCase())
        }
        education.setOnClickListener{
            categoryImage(education.text.toString().toLowerCase())
        }
        feelings.setOnClickListener{
            categoryImage(feelings.text.toString().toLowerCase())
        }
        health.setOnClickListener{
            categoryImage(health.text.toString().toLowerCase())
        }
        people.setOnClickListener{
            categoryImage(people.text.toString().toLowerCase())
        }
        religion.setOnClickListener{
            categoryImage(religion.text.toString().toLowerCase())
        }
        places.setOnClickListener{
            categoryImage(places.text.toString().toLowerCase())
        }
        animals.setOnClickListener{
            categoryImage(animals.text.toString().toLowerCase())
        }
        industry.setOnClickListener{
            categoryImage(industry.text.toString().toLowerCase())
        }
        computer.setOnClickListener{
            categoryImage(computer.text.toString().toLowerCase())
        }
        food.setOnClickListener{
            categoryImage(food.text.toString().toLowerCase())
        }
        sports.setOnClickListener{
            categoryImage(sports.text.toString().toLowerCase())
        }
        transportation.setOnClickListener{
            categoryImage(transportation.text.toString().toLowerCase())
        }
        travel.setOnClickListener{
            categoryImage(travel.text.toString().toLowerCase())
        }
        buildings.setOnClickListener{
            categoryImage(buildings.text.toString().toLowerCase())
        }
        business.setOnClickListener{
            categoryImage(business.text.toString().toLowerCase())
        }
        music.setOnClickListener{
            categoryImage(music.text.toString().toLowerCase())
        }
    }

    private fun categoryImage(categoryName: String) {
        val intent= Intent(this@HomeScreenActivity,CategoryImagesActivity::class.java)
        intent.putExtra("categoryName",categoryName)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //noinspection SimplifiableIfStatement
        if (item.itemId == R.id.action_fav) {
            startActivity(Intent(this@HomeScreenActivity, FavouriteActivity::class.java))
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}