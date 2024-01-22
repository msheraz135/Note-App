package com.example.chapter14notekeepingapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chapter14notekeepingapp.R
import com.example.chapter14notekeepingapp.databinding.ActivityMainBinding
import com.example.chapter14notekeepingapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var binding :ActivitySettingsBinding
    private var showDividers: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("note keeping app", Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean("dividers", true)

        val switch = binding.switch1
        switch.isChecked = showDividers

        switch.setOnCheckedChangeListener { _, isChecked ->
            showDividers= isChecked


        }

        }

    override fun onPause() {
        super.onPause()
        val pref = getSharedPreferences("note keeping app", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("dividers",showDividers)
        editor.apply()
    }

    }
/**
 *
 * While both Shared Preferences and Database can store notes, using them together has pros and cons, and the best approach depends on your specific needs.
 *
 * Pros:
 *
 * Shared Preferences:
 * Faster access: Ideal for small data like a few recently accessed notes.
 * Simple implementation: Easy to use for basic storage and retrieval.
 * Limited data size: Suitable for a small list of notes with minimal information.
 * Database:
 * Larger data storage: Handles bigger lists of notes with greater complexity.
 * Persistence: Data persists even after app restart, unlike Shared Preferences.
 * Query features: Allows searching, filtering, and sorting notes efficiently.
 * Cons:
 *
 * Shared Preferences:
 * Limited data complexity: Not suitable for large or complex note data.
 * Non-persistence: Data gets lost when the app is uninstalled or data is cleared.
 * No query capabilities: Limited to basic retrieval, cannot filter or sort.
 * Database:
 * Slower access: Accessing all notes might be slower than Shared Preferences.
 * More complex implementation: Requires setting up a database schema and CRUD operations.
 * Overkill for small datasets: Using a database for a few notes might be redundant.
 * Do's and Don'ts:
 *
 * Do:
 * Use Shared Preferences for:
 * Storing recently accessed notes for quick access.
 * Saving a temporary note draft before saving it to the database.
 * Use Database for:
 * Storing the main list of notes and their full data.
 * Enabling search, filtering, and sorting capabilities.
 * Ensuring persistence of data across app restarts.
 * Don't:
 * Duplicate all note data in both Shared Preferences and Database.
 * Rely solely on Shared Preferences for large or complex notes.
 * Use Shared Preferences for persistent data storage.
 * Recommendation:
 *
 * For your case, combining both approaches can be beneficial. Store recently accessed notes in Shared Preferences for quick retrieval, and maintain the main list of notes with full data in the database for persistence and advanced functionalities. However, if you only have a few notes with minimal information, using Shared Preferences alone might be sufficient.
 *
 * Ultimately, the decision depends on your specific needs and the complexity of your note data and functionality.
 *
 * I hope this explanation helps you make an informed decision about using Shared Preferences and Database together for your note-keeping app!*/