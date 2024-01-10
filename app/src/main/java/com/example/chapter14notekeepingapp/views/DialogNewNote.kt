package com.example.chapter14notekeepingapp.views

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.chapter14notekeepingapp.R
import com.example.chapter14notekeepingapp.data.Note

class DialogNewNote : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.requireActivity()!!)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_new_note, null)

        val textTitle = dialogView.findViewById(R.id.editTitle) as EditText
        val textDes = dialogView.findViewById(R.id.editDescription) as EditText
        val checkBoxTodo = dialogView.findViewById(R.id.checkBoxTodo) as CheckBox
        val checkBoxIdea = dialogView.findViewById(R.id.checkBoxIdea) as CheckBox
        val checkBoxImportant = dialogView.findViewById<CheckBox>(R.id.checkBoxImportant)
        val btCancel = dialogView.findViewById(R.id.btnCancel) as Button
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)
        btnOk.isEnabled = false
        btnOk.text="Select a check Box"
        val checkBoxListner = listOf( checkBoxTodo, checkBoxIdea, checkBoxImportant)
            .map { checkBox -> checkBox.setOnCheckedChangeListener{
                    _,isChecked ->
                val checkCount = listOf(checkBoxTodo, checkBoxIdea, checkBoxImportant)
                    .count { it.isChecked } // Count the checked boxes directly
                btnOk.isEnabled = checkCount==1
                btnOk.text= when{
                    checkCount ==1 -> "OK"
                    checkCount>1 ->  "select 1 Checkbox"
                    else -> "Select a check Box"
                }
            } }
        builder.setView(dialogView).setMessage("create a new Note")
        btCancel.setOnClickListener {
            dismiss()
        }
        btnOk.setOnClickListener {
            if (!btnOk.isEnabled)
            {
                Toast.makeText(requireActivity(), "Please select a checkbox to enable the OK button", Toast.LENGTH_SHORT).show()
            }
            val NewNote = Note()
            NewNote.title = textTitle.text.toString()
            NewNote.description = textDes.text.toString()
            NewNote.idea = checkBoxIdea.isChecked
            NewNote.important = checkBoxImportant.isChecked
            NewNote.todo = checkBoxTodo.isChecked

            val callingActivity = requireActivity() as MainActivity
            callingActivity.createNewNote(NewNote)
            dismiss()
        }
        return builder.create()


    }

}