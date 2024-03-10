package com.example.android.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<ToDoItem>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.item_recycler_title)
        val description: TextView = itemView.findViewById(R.id.item_recycler_description)
        val number: TextView = itemView.findViewById(R.id.item_recycler_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)
        holder.title.text = mList[position].title
        holder.description.text = mList[position].description
        holder.number.text = mList[position].number.toString()

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}