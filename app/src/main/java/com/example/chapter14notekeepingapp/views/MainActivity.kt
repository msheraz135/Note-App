package com.example.chapter14notekeepingapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter14notekeepingapp.Database.NoteDatabaseHelper
import com.example.chapter14notekeepingapp.R
import com.example.chapter14notekeepingapp.adapters.NoteAdapter
import com.example.chapter14notekeepingapp.data.Note
import com.example.chapter14notekeepingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: NoteDatabaseHelper
    private var recyclerView : RecyclerView? = null
    private  var noteAdapter: NoteAdapter? = null
    private  var noteList = ArrayList<Note>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        dbHelper = NoteDatabaseHelper(this)
        noteList= dbHelper.getNotes() as ArrayList<Note>


        recyclerView = binding.recycleView.recyclerView2
        noteAdapter = NoteAdapter(this, noteList)
        val layoutManger = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager=layoutManger
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter=noteAdapter

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.buttonMain.setOnClickListener {
//            val dialog = dialogShowNote()
//            dialog.sendNoteSelected(tempNote)
//            dialog.show(supportFragmentManager, "")
//

        binding.fab.setOnClickListener {
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun onPause() {
        super.onPause()
        saveNotes()
    }

    private fun saveNotes() {
        val db = dbHelper.writableDatabase

        try {
            for (note in noteList)
            {
                if(note.id ==0)
                {
                    dbHelper.insertNotes(note)
                }
                else
                {
                   //dbHelper.updateNotes(note)
                }
            }
        }
        finally {
            //db.close()
        }
    }


    fun ShowNote(position :Int)
        {
            val note = noteList[position]
            val dialog = dialogShowNote()
            dialog.sendNoteSelected(note)
            dialog.show(supportFragmentManager, "")
        }


    fun createNewNote(newNote: Note) {
        noteList.add(newNote)
        noteAdapter!!.notifyDataSetChanged()
        //tempNote = newNote

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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}