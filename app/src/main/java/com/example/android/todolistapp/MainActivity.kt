package com.example.android.todolistapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.android.todolistapp.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var stubContainer: LinearLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var db: AppDatabase

    //№3 Создаем LiveData для обработки данных
    private lateinit var toDoLiveData: LiveData<List<ToDoItem>>
    private lateinit var data: List<ToDoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("lstag", "OnCreate been started")

        // getting the recyclerview by its id
        recyclerview = findViewById(R.id.mainRecyclerView)
        stubContainer = findViewById(R.id.main_no_item_container)
        fab = findViewById(R.id.main_fab)

        // №1 Появление диалогового окна для сбора данных
        // Достаем данные из shared preferences
        fab.setOnClickListener() {

            val dialog = CustomDialog(this, true, null)
            dialog.show()
        }

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        adapter = CustomAdapter(mutableListOf(), this)

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
            data = it
            adapter.updateList(it)
            screenDataValidation(it)
            Log.d("roomcheck", "-> $it")
        })

        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_delete_24)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor("#f44336")
        val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                // Draw the red delete background
                background.color = backgroundColor
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(canvas)

                // Calculate position of the icon
                val iconMargin = (itemHeight - intrinsicHeight!!) / 2
                val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - intrinsicWidth!!
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + intrinsicHeight

                // Draw the delete icon
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(canvas)
            }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedToDoItem: ToDoItem =
                    data.get(position)

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                data.toMutableList().removeAt(position)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(position)

                // below line is to display our snackbar with action.
                Snackbar.make(recyclerview, "Deleted " + deletedToDoItem.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            data.toMutableList().add(position, deletedToDoItem)

                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()
                deleteItem(deletedToDoItem)
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerview)

    }

    private fun screenDataValidation(list: List<ToDoItem>) {
        if (list.isEmpty()) {
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

    fun updateItem(item: ToDoItem) {
        db.userDao().updateItem(item)
    }

    fun deleteItem(item: ToDoItem) {
        db.userDao().deleteItem(item)
    }


    override fun itemClicked(item: ToDoItem) {
        val dialog = CustomDialog(this, false, item)
        dialog.show()
       Log.d("itemClicked", "itemClicked $item")
    }
}