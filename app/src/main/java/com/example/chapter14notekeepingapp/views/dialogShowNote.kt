package com.example.chapter14notekeepingapp.views

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.chapter14notekeepingapp.R
import com.example.chapter14notekeepingapp.data.Note

class dialogShowNote : DialogFragment() {
    var note: Note? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.requireActivity()!!)
        //layout OBject is created
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_show_note, null)
        // data Set in  our XMLOBect
        val textViewTitle = dialogView.findViewById(R.id.textViewTile) as TextView
        val textViewDes = dialogView.findViewById(R.id.textViewDescription) as TextView
        val textViewImportant = dialogView.findViewById(R.id.textViewImportant) as TextView
        val textViewIdea = dialogView.findViewById(R.id.textViewIdea) as TextView
        val textViewTodo = dialogView.findViewById(R.id.textViewToDo) as TextView
        textViewTitle.text = note?.title
        textViewDes.text = note?.description
        if (!(note!!.important)) {
            textViewImportant.visibility = View.INVISIBLE
        }
        else{
            textViewImportant.visibility = View.VISIBLE
        }
        textViewTodo.visibility = if (note?.todo == true) View.VISIBLE else View.INVISIBLE
        textViewIdea.visibility = if (note?.idea == true) View.VISIBLE else View.INVISIBLE
//        if (!note!!.idea) {
//            textViewIdea.visibility = View.GONE
//        }
        val btnOk = dialogView.findViewById<Button>(R.id.btnOKFromShow)

        builder.setView(dialogView).setMessage("Show Note")

        btnOk.setOnClickListener {
            dismiss()
        }
        return builder.create()       }//onCreateDialog Closed
    fun sendNoteSelected(selectedNote: Note?) {//recivenote selected
        this.note = selectedNote    }//fun closed

}