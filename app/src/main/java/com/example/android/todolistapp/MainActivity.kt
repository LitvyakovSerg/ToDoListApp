package com.example.android.todolistapp

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnItemClick {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var stubContainer: LinearLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: CustomAdapter


    private lateinit var data: List<ToDoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        swipeImplementation()
        observers()

        fab.setOnClickListener() {

            val dialogFragment = CustomDialog(true, null)
            dialogFragment.show(supportFragmentManager, getString(R.string.custom_dialog))
        }

        mainViewModel.getAllData()

    }

    private fun observers() {
        mainViewModel.todoItemListResult.observe(this, Observer {
            data = it
            adapter.updateList(it)
            screenDataValidation(it)

        })
    }

    @SuppressLint("ResourceType")
    private fun swipeImplementation() {
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_delete_24)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor(getString(R.color.red))

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
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
                Snackbar.make(recyclerView, "Deleted " + deletedToDoItem.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        getString(R.string.undo),
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            data.toMutableList().add(position, deletedToDoItem)
                            mainViewModel.insertItem(deletedToDoItem)

                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()
                mainViewModel.deleteItem(deletedToDoItem)
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView)

    }

    private fun initView() {
        recyclerView = findViewById(R.id.mainRecyclerView)
        stubContainer = findViewById(R.id.main_no_item_container)
        fab = findViewById(R.id.main_fab)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun screenDataValidation(list: List<ToDoItem>) {
        if (list.isEmpty()) {
            setupStub(showStub = true, showRecycler = false)
        } else {
            setupStub(showStub = false, showRecycler = true)
        }
    }

    private fun setupStub(showStub: Boolean, showRecycler: Boolean) {
        stubContainer.isVisible = showStub
        recyclerView.isVisible = showRecycler
    }

    override fun itemClicked(item: ToDoItem) {
        val dialogFragment = CustomDialog(false, item)
        dialogFragment.show(supportFragmentManager, getString(R.string.custom_dialog))
    }
}