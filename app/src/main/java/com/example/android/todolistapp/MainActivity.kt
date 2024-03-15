package com.example.android.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("lstag", "OnCreate been started")

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.mainRecyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ToDoItem>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (item in 1..15) {
            data.add(ToDoItem("title", "description", item))
        }

        val listSize = data.size
        Log.d("lstag", "list size $listSize")

        if (listSize > 0) {

        } else {

        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        Log.d("lstag", "OnCreate been finished")
    }
}