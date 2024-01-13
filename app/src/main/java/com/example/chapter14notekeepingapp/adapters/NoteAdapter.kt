package com.example.chapter14notekeepingapp.adapters
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter14notekeepingapp.views.MainActivity
import com.example.chapter14notekeepingapp.R
import com.example.chapter14notekeepingapp.R.id
import com.example.chapter14notekeepingapp.data.Note
import java.lang.Math.abs


class NoteAdapter(private val mainActivity: MainActivity, private val noteList: MutableList<Note>) :
    RecyclerView.Adapter<NoteAdapter.ListitemHolder>() {
//change List to MutableList<Note> here and in databaseHelper as well.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListitemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.listitem, parent, false)
        return ListitemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListitemHolder, position: Int) {
        val note = noteList[position]
        holder.title.text = note.title
        holder.description.text =
            note.description!!//.substring(0, 15)+".."
        when {
            note.idea -> holder.status.text =
                mainActivity.resources.getString(R.string.idea_text)

            note.important -> holder.status.text =
                mainActivity.resources.getString(R.string.important_text)

            note.todo -> holder.status.text =
                mainActivity.resources.getString(R.string.to_do_text)
        }
    }

    override fun getItemCount(): Int {
        if (noteList != null) {
            return noteList.size
        } else return -1
    }



    inner class ListitemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        internal val title = view.findViewById(id.textViewTitle) as TextView
        internal val description = view.findViewById(id.textViewDescription) as TextView
        internal val status = view.findViewById(id.textViewStatus) as TextView

        init {
            view.isClickable = true
            view.setOnClickListener(this)
            view.setOnLongClickListener {
                //remove unnecessary code and call it from main activity
                //database implementation in main activity
                //update
                mainActivity.updateSelected(adapterPosition)
                true
            }
        }
        override fun onClick(view: View?) {
            //show note
            mainActivity.ShowNote(adapterPosition)

        }
    }
}


