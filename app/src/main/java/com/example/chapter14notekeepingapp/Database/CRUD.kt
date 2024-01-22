package com.example.chapter14notekeepingapp.Database

import android.content.ContentValues
import android.content.Context
import com.example.chapter14notekeepingapp.data.Note

class CRUD(context: Context) {
    lateinit var dbhelper: NoteDatabaseHelper

    init {
        dbhelper = NoteDatabaseHelper(context)
    }

    fun updateNote(note: Note): Int {
        val db = dbhelper.writableDatabase
        val values = ContentValues()

        values.put(NoteDatabaseHelper.KEY_TITLE, note.title)
        values.put(NoteDatabaseHelper.KEY_DESCRIPTION, note.description)
        values.put(NoteDatabaseHelper.KEY_IDEA, if (note.idea) 1 else 0)
        values.put(NoteDatabaseHelper.KEY_IMPORTANT, if (note.important) 1 else 0)
        values.put(NoteDatabaseHelper.KEY_TODO, if (note.todo) 1 else 0)

        val rowsAffected = db.update(
            NoteDatabaseHelper.TABLE_NOTES,
            values,
            "$NoteDatabaseHelper.KEY_ID=?",
            arrayOf(note.id.toString())
        )
        //db.close()

        return rowsAffected
    }


}