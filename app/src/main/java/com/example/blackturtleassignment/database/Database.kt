package com.example.blackturtleassignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blackturtleassignment.model.CategoryDetails

@Database(entities = arrayOf(CategoryDetails.Hit::class), version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun favDao(): FAV_DAO
}
