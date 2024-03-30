package com.example.android.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var stubContainer: LinearLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("lstag", "OnCreate been started")

        // getting the recyclerview by its id
        recyclerview = findViewById(R.id.mainRecyclerView)
        stubContainer = findViewById(R.id.main_no_item_container)
        fab = findViewById(R.id.main_fab)

        fab.setOnClickListener() {
            val dialog = CustomDialog(this)
            dialog.show()
        }

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ToDoItem>()

        // This loop will create 20 Views containing
        // the image with the count of view
//        for (item in 1..15) {
//            data.add(ToDoItem("title", "description", item))
//        }
//
//        val listSize = data.size
//        Log.d("lstag", "list size $listSize")

        stubContainerHide(data)

        // This will pass the ArrayList to our Adapter
        adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        Log.d("lstag", "OnCreate been finished")
    }

    fun stubContainerHide(data: ArrayList<ToDoItem>) {
        if (data.isEmpty()) {
            Log.d("testingIfElse", "List is empty")
            stubContainer.visibility = VISIBLE
            recyclerview.visibility = INVISIBLE
        } else {
            Log.d("testingIfElse", "List is NOT empty")
            stubContainer.visibility = INVISIBLE
            recyclerview.visibility = VISIBLE
        }
    }

    fun addItem(item: ToDoItem) {
        stubContainer.visibility = INVISIBLE
        recyclerview.visibility = VISIBLE
        adapter.addItem(item)
    }
}