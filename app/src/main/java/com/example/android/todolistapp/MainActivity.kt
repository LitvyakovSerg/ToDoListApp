package com.example.android.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.android.todolistapp.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var stubContainer: LinearLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var db: AppDatabase

    //№3 Создаем LiveData для обработки данных
    private lateinit var toDoLiveData: LiveData<List<ToDoItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("lstag", "OnCreate been started")

        // getting the recyclerview by its id
        recyclerview = findViewById(R.id.mainRecyclerView)
        stubContainer = findViewById(R.id.main_no_item_container)
        fab = findViewById(R.id.main_fab)

//        №1 Появление диалогового окна для сбора данных
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

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        )
            .allowMainThreadQueries()
            .build()

        toDoLiveData = db.userDao().getAllItems()

        // №4 Отображаем полученные данные в списке
        toDoLiveData.observe(this, Observer {
            adapter.updateList(it)

            Log.d("roomcheck", "-> $it")
        })

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

    //№2 отправляем данные в БД
    // 2.2 отправка собранных данных в БД
    fun addItem(item: ToDoItem) {
        stubContainer.visibility = INVISIBLE
        recyclerview.visibility = VISIBLE
        db.userDao().insertItem(item)
    }
}