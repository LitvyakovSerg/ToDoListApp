package com.example.android.todolistapp

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CustomDialog(var activity: MainActivity, private val isNewItem: Boolean, private val item: ToDoItem?) : Dialog(activity), View.OnClickListener {

    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    private lateinit var inputFieldTitle : EditText
    private lateinit var inputFieldDescription : EditText
    private lateinit var inputFieldNumber : EditText
    private lateinit var dialogLabel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_template)
        inputFieldTitle = findViewById(R.id.dialog_input_title)
        inputFieldDescription = findViewById(R.id.dialog_input_description)
        inputFieldNumber = findViewById(R.id.dialog_input_number)
        dialogLabel = findViewById(R.id.dialog_label)

        if (isNewItem) {
           // Если создается новая ячейка, то этот сценарий
            createNewItem()

        } else {
           // Если обновляется существующая, то этот
            updateExistingItem()
        }

        initViews()
        dialogSizeControl()
    }

    private fun updateExistingItem() {
        Log.d("dialogTest", "updateExistingItem been called")
        dialogLabel.text = "Update Item"
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)
        inputFieldNumber.setText(item?.number.toString())

    }

    private fun createNewItem() {
        Log.d("dialogTest", "createNewItem been called")

    }

    private fun initViews() {
        okButton = findViewById<Button>(R.id.dialog_ok_button)
        cancelButton = findViewById<Button>(R.id.dialog_cancel_button)
        okButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
    }

    /**
     * This need to control dialog window size
     * */

    private fun dialogSizeControl() {

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(this.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        this.window?.attributes = lp
    }

    /**
    * This function handles clicks
    * */

    override fun onClick(view: View) {
        when (view.id) {
            R.id.dialog_ok_button -> {
                okButtonClicker()
            }
            R.id.dialog_cancel_button -> dismiss()
            else -> {
            }
        }
        dismiss()
    }

    //№2 отправляем данные в БД
    // 2.1 вытаскиваем данные из полей ввода
    private fun okButtonClicker() {
        if (isNewItem) {
            // Если создается новая ячейка, то этот сценарий
            okNewItemBeenClicked()

        } else {
            // Если обновляется существующая, то этот
            okUpdateItemBeenClicked()
        }

        dismiss()
    }

    private fun okUpdateItemBeenClicked() {
        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        val inputNumberResult = inputFieldNumber.text.toString().toInt()

        item?.id?.let { ToDoItem(it, inputTitleResult, inputDescriptionResult, inputNumberResult) }
            ?.let { activity.updateItem(it) }
    }

    private fun okNewItemBeenClicked() {
        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        val inputNumberResult = inputFieldNumber.text.toString().toInt()

        activity.addItem(ToDoItem(0,inputTitleResult, inputDescriptionResult, inputNumberResult))
    }
}