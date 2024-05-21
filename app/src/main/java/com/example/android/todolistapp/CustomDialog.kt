package com.example.android.todolistapp

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.android.todolistapp.data.PrefsManagerImpl.Companion.PREFS_DESCRIPTION_KEY
import com.example.android.todolistapp.data.PrefsManagerImpl.Companion.PREFS_NUMBER_KEY
import com.example.android.todolistapp.data.PrefsManagerImpl.Companion.PREFS_TITLE_KEY


class CustomDialog(
//    var activity: MainActivity,
    private val isNewItem: Boolean,
    private val item: ToDoItem?
    ) : DialogFragment(), View.OnClickListener {

    private val customDialogViewModel: CustomDialogViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription : EditText
    private lateinit var inputFieldNumber : EditText
    private lateinit var dialogLabel : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_template, container, false)

        initViews(view)

        if (isNewItem) {
            // Если создается новая ячейка, то этот сценарий
            customDialogViewModel.getToDoItemFromPrefs()
//            createNewItem()

        } else {
            // Если обновляется существующая, то этот
            dialogLabel.text = getString(R.string.update_item)
            inputFieldTitle.setText(item?.title)
            inputFieldDescription.setText(item?.description)
            inputFieldNumber.setText(item?.number.toString())
//            updateExistingItem()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        dialogSizeControl()
        observers()
    }

    private fun observers() {
        customDialogViewModel.todoItemResult.observe(this, Observer {
            if (isNewItem) {
                inputFieldTitle.setText(it.title)
                inputFieldDescription.setText(it.description)
                inputFieldNumber.setText(it.number)
            }

        })
    }


//    private fun updateExistingItem() {
//        Log.d("dialogTest", "updateExistingItem been called")
//        dialogLabel.text = "Update Item"
//        inputFieldTitle.setText(item?.title)
//        inputFieldDescription.setText(item?.description)
//        inputFieldNumber.setText(item?.number.toString())
//
//    }
//
//    private fun createNewItem() {
//        customDialogViewModel.getToDoItemFromPrefs()
//
//
//    }

    private fun initViews(view: View) {
        inputFieldTitle = view.findViewById(R.id.dialog_input_title)
        inputFieldDescription = view.findViewById(R.id.dialog_input_description)
        inputFieldNumber = view.findViewById(R.id.dialog_input_number)
        dialogLabel = view.findViewById(R.id.dialog_label)
        okButton = view.findViewById<Button>(R.id.dialog_ok_button)
        cancelButton = view.findViewById<Button>(R.id.dialog_cancel_button)
        okButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)

    }

    /**
     * This need to control dialog window size
     * */

    private fun dialogSizeControl() {

        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ActionBar.LayoutParams.MATCH_PARENT
        params.height = ActionBar.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    /**
    * This function handles clicks
    * */

    override fun onClick(view: View) {
        when (view.id) {
            R.id.dialog_ok_button -> {
                okButtonClicker()
            }
            R.id.dialog_cancel_button -> {
                dismiss()
            }
            else -> {
                elseBeenClicked()
            }
        }
//        dismiss()
    }

    private fun elseBeenClicked() {
        TODO("Not yet implemented")
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
        val inputNumberResult = inputFieldNumber.text.toString()
//        val inputNumberResult = inputFieldNumber.text.toString().toInt()

        item?.id?.let { ToDoItem(it, inputTitleResult, inputDescriptionResult, inputNumberResult) }
            ?.let { mainViewModel.updateItem(it) }
    }

    private fun okNewItemBeenClicked() {
        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        val inputNumberResult = inputFieldNumber.text.toString()
//        val inputNumberResult = inputFieldNumber.text.toString().toInt()
        mainViewModel.insertItem(ToDoItem(0,inputTitleResult, inputDescriptionResult, inputNumberResult))
        inputFieldTitle.text.clear()
        inputFieldDescription.text.clear()
        inputFieldNumber.text.clear()

    }

    override fun onStop() {
        super.onStop()
        if (isNewItem) {
                val inputTitleResult = inputFieldTitle.text.toString()
                val inputDescriptionResult = inputFieldDescription.text.toString()
                val inputNumberResult = inputFieldNumber.text.toString()
                customDialogViewModel.saveDataInPrefs(PREFS_TITLE_KEY, inputTitleResult)
                customDialogViewModel.saveDataInPrefs(PREFS_DESCRIPTION_KEY, inputDescriptionResult)
                customDialogViewModel.saveDataInPrefs(PREFS_NUMBER_KEY, inputNumberResult)

        }

    }
}