package com.example.chapter14notekeepingapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.chapter14notekeepingapp.data.Note

/**
 * Key Points:
 *
 * 1- Database Helper Class: Correctly extends SQLiteOpenHelper for database management.
 * 2- Constant Definitions: Uses a companion object to store database-related constants, improving readability and maintainability.
 * 3- Table Creation: Uses execSQL to execute a SQL query that creates the notes table with the specified columns, including an auto-incrementing primary key id.
 * 4- Upgrade Handling: Employs onUpgrade to drop the existing table if the database version changes, preparing for re-creation with potential schema updates.
 * 5-
 * Additional Considerations:
 *
 * 1` Database Upgrade Strategy: While dropping the table is a simple approach for version 1, consider more sophisticated upgrade strategies in future versions to preserve user data.
 * 1` Error Handling: Incorporate error handling mechanisms for database operations to ensure robustness and provide informative feedback to the user.
 * 3` Database Transactions: Use database transactions for complex operations involving multiple queries to ensure data consistency and prevent partial updates.
 * 4` Query Optimization: For large datasets, explore query optimization techniques to enhance performance.*/
/** Explanation:

1.	SQLiteOpenHelper: Extends the SQLiteOpenHelper class to manage database creation and upgrades.
2.	Constructor: Takes the context to access the application's data directory.
3.	onCreate: Called when the database is created for the first time:
a.	Creates the notes table with columns:
•	id: Integer for primary key (auto-generated).
•	title: Text for note title.
•	description: Text for note content.
•	idea, important, todo: Integers (1 or 0) representing Boolean flags.
4.	onUpgrade: Reserved for handling database upgrades, not used in this initial setup.
To use this database:

1.	Create an instance of NoteDatabaseHelper in your activity or fragment.
2.	Use the getReadableDatabase() or getWritableDatabase() methods to access the database for reading or writing operations.
3.	Execute SQL queries to create, read, update, and delete notes using the defined table structure.
 */

class NoteDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        val query = "CREATE TABLE $TABLE_NOTES (" +
                "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_TITLE TEXT, " +
                "$KEY_DESCRIPTION TEXT," +
                "$KEY_TODO INTEGER, " +
                "$KEY_IMPORTANT INTEGER, " +
                "$KEY_IDEA INTEGER)"
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
    }

    companion object {
        protected const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 1

        public const val TABLE_NOTES = "notes"
        public const val KEY_ID = "id"
        public const val KEY_TITLE = "title"
        public const val KEY_DESCRIPTION = "description"
        public const val KEY_IDEA = "idea"
        public const val KEY_IMPORTANT = "important"
        public const val KEY_TODO = "todo"
    }

    fun insertNotes(note: Note) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            // key -> value
            put(KEY_TITLE, note.title)
            put(KEY_DESCRIPTION, note.description)
            put(KEY_TODO, if (note.todo) 1 else 0) //true 1 flase 0
            put(KEY_IMPORTANT, if (note.important) 1 else 0)
            put(KEY_IDEA, if (note.idea) 1 else 0)
        }

        db.insert(TABLE_NOTES, null, contentValues)
        //db.close()
    }

    fun getNotes(): MutableList<Note> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NOTES, null, null, null, null, null, null)
        val notes = mutableListOf<Note>() // note ==3 note
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            val description = cursor.getString(2)
            val todo = cursor.getInt(3) == 1
            val important = cursor.getInt(4) == 1 // 1==1
            val idea = cursor.getInt(5) == 1
            val note = Note(id, title, description, todo, important, idea)
            notes.add(note)
        }
        cursor.close()
        //db.close()
        return notes
    }

    fun deleteNote(note: Note): Int {
        val db = this.writableDatabase
        val rowsAffected = db.delete(TABLE_NOTES, "$KEY_ID=?", arrayOf(note.id.toString()))
        //db.close()
        return rowsAffected
    }

    fun updateNote(note: Note): Int {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_TITLE, note.title)
        values.put(KEY_DESCRIPTION, note.description)
        values.put(KEY_IDEA, if (note.idea) 1 else 0)
        values.put(KEY_IMPORTANT, if (note.important) 1 else 0)
        values.put(KEY_TODO, if (note.todo) 1 else 0)

        val rowsAffected = db.update(TABLE_NOTES, values, "$KEY_ID=?", arrayOf(note.id.toString()))
        //db.close()

        return rowsAffected
    }
}

