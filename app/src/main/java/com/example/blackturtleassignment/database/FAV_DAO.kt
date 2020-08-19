package com.example.blackturtleassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.blackturtleassignment.model.CategoryDetails

@Dao
interface FAV_DAO {
    @Insert
    fun savFav(fav: CategoryDetails.Hit):Long

    @Query("select * from favourite_entity")
    fun readFav():List<CategoryDetails.Hit>

    @Query("DELETE FROM favourite_entity WHERE id=:fav_id")
    fun deleteFav(fav_id:Int):Int

    @Query("SELECT * FROM favourite_entity WHERE id=:fav_id")
    fun checkifExist(fav_id:Int):Long
}
