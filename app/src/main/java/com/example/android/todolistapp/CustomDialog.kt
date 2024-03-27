package com.example.android.todolistapp

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class CustomDialog(var activity: MainActivity) : Dialog(activity), View.OnClickListener {

    var yes: Button? = null
    var no: Button? = null
    private lateinit var inputField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_template)
        inputField = findViewById(R.id.dialogInput)

        initViews()
        dialogSizeControl()
    }

    private fun initViews() {
        yes = findViewById<Button>(R.id.dialogOkButton)
        no = findViewById<Button>(R.id.dialogCancelButton)
        yes?.setOnClickListener(this)
        no?.setOnClickListener(this)
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
            R.id.dialogOkButton -> {
                okButtonClicker()
            }
            R.id.dialogCancelButton -> dismiss()
            else -> {
            }
        }
        dismiss()
    }

    private fun okButtonClicker() {
        val inputResult = inputField.text
        activity.addItem(inputResult.toString())
        dismiss()
    }
}