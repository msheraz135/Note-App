package com.example.chapter14notekeepingapp.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter14notekeepingapp.Database.NoteDatabaseHelper
import com.example.chapter14notekeepingapp.R
import com.example.chapter14notekeepingapp.adapters.SwipeToDeleteCallback
import com.example.chapter14notekeepingapp.adapters.NoteAdapter
import com.example.chapter14notekeepingapp.data.Note
import com.example.chapter14notekeepingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: NoteDatabaseHelper
    private var recyclerView: RecyclerView? = null
    private var noteAdapter: NoteAdapter? = null
    private var noteList = mutableListOf<Note>()
    private var showDividers: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        dbHelper = NoteDatabaseHelper(this)
        noteList = dbHelper.getNotes()
        if (noteList.isEmpty()) {
            dummyDatainList()
            // Save dummy data to the database
            saveNotes()
        }

        recyclerView = binding.recycleView.recyclerView2
        noteAdapter = NoteAdapter(this, noteList)
        val layoutManger = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManger
        recyclerView!!.itemAnimator = DefaultItemAnimator()

        if(showDividers)
        {
            recyclerView!!.addItemDecoration(
                DividerItemDecoration(
                    this, LinearLayoutManager.VERTICAL
                )
            )
        }
        else{
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }

        recyclerView!!.adapter = noteAdapter
        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(noteAdapter!!,this))
        itemTouchHelper.attachToRecyclerView(recyclerView)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.buttonMain.setOnClickListener {
//            val dialog = dialogShowNote()
//            dialog.sendNoteSelected(tempNote)
//            dialog.show(supportFragmentManager, "")


        binding.fab.setOnClickListener {
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun onResume() {
        super.onResume()
        val pref = getSharedPreferences("note keeping app", Context.MODE_PRIVATE)
        showDividers = pref.getBoolean("dividers", true)

    }

    private fun dummyDatainList() {
        for (i in 1..10) {
            val dummyNote = Note(
                id = 0,
                title = "Dummy Title $i",
                description = "Dummy Description $i",
                todo = i % 2 == 0,
                important = i % 3 == 0,
                idea = i % 4 == 0
            )
            noteList.add(dummyNote)
        }
    }

    override fun onPause() {
        super.onPause()
        saveNotes()
    }

    private fun saveNotes() {
        val db = dbHelper.writableDatabase

        try {
            for (note in noteList) {
                if (note.id == 0) {
                    dbHelper.insertNotes(note)
                } else {
                    dbHelper.updateNote(note)
                }
            }
        } finally {
            //db.close()
        }
    }

    fun updateSelected(adapterPosition: Int) {
        Toast.makeText(
            this,
            "Apos:" + adapterPosition.toString() + "\n Nid:" + noteList[adapterPosition].id + "LS: ${noteList.size}",
            Toast.LENGTH_SHORT
        ).show()
        Log.i(
            "update",
            "Adp position:" + adapterPosition.toString() + "\n Nid:" + noteList[adapterPosition].id + noteList[adapterPosition].title + "\n List Size: ${noteList.size}"
        )
    }

    fun deleteSelected(adapterPosition: Int) {
        // Database
        val db = dbHelper.writableDatabase
        db.use { db ->
            if (adapterPosition >= 0 && adapterPosition < noteList.size) {
                val deletedNote = noteList.removeAt(adapterPosition)
                noteAdapter?.notifyItemRemoved(adapterPosition)

                Log.i("database", "deleteSelected: value of adapter position: $adapterPosition")
                Log.i("database", "deleteSelected: note id ${deletedNote.id}")
                Log.i("database", "deleteSelected: note title ${deletedNote.title}")
                Log.i("database", "deleteSelected: notelist size ${noteList.size}")

                dbHelper.deleteNote(deletedNote)
            } else {
                Log.e("database", "deleteSelected: Invalid adapter position $adapterPosition")
            }
        }
    }


    fun createNewNote(newNote: Note) {
        noteList.add(newNote)
        noteAdapter!!.notifyDataSetChanged()
        //tempNote = newNote
    }

    fun ShowNote(position: Int) {
        val note = noteList[position]
        val dialog = dialogShowNote()
        dialog.sendNoteSelected(note)
        dialog.show(supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }




}