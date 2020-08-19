package com.example.blackturtleassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blackturtleassignment.R
import com.example.blackturtleassignment.model.CategoryDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout.view.*


class CategoryDetailsAdapter(
    private val users: ArrayList<CategoryDetails.Hit>, private var mCallback:ImageClickCallback,private val type:String) : RecyclerView.Adapter<CategoryDetailsAdapter.DataViewHolder>() {
    interface ImageClickCallback {
        fun onImageClickListener(imageDataCallBack: CategoryDetails.Hit?)
        fun onAddFavClickListener(imageDataCallBack: CategoryDetails.Hit?)
        fun onRemoveFavClickListener(imageDataCallBack: CategoryDetails.Hit?)
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            user: CategoryDetails.Hit,
            type: String
        ) {
            if(type.equals("fav"))
            {
                itemView.removeFav.visibility=View.VISIBLE
            }
            else
            {
                itemView.addFav.visibility=View.VISIBLE
            }
            itemView.textViewUserName.text = user.user
            itemView.textViewUserEmail.text = user.tags
            /*Glide.with(itemView.imageViewAvatar.context)
                .load(user.previewURL)
                .into(itemView.imageViewAvatar)*/
            Picasso.get().load(user.previewURL).into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(
            users[position]
        ,type)
        holder.itemView.imageViewAvatar.setOnClickListener {
            mCallback.onImageClickListener(
                users[position]
            )
        }
        holder.itemView.addFav.setOnClickListener {
            mCallback.onAddFavClickListener(users[position])
        }
        holder.itemView.removeFav.setOnClickListener {
            mCallback.onRemoveFavClickListener(users[position])
        }
    }

    fun addData(list: List<CategoryDetails.Hit>) {
        users.addAll(list)
    }

}