package com.example.chapter14notekeepingapp.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter14notekeepingapp.adapters.NoteAdapter
import com.example.chapter14notekeepingapp.views.MainActivity

class SwipeToDeleteCallback(private val adapter: NoteAdapter, private val mainActivity: MainActivity)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        //var mainactivity: MainActivity = viewHolder.itemView.context as MainActivity
        //adapter.deleteItem(position)
        //change //previously handled in Adpter now handled in mainActivity
        mainActivity.deleteSelected(position)

    }

}