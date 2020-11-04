package com.example.realmdatabasedemo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdatabasedemo.R
import com.example.realmdatabasedemo.data.Notess
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        buttonAddNoteFromNetwork.setOnClickListener {
            mainViewModel.insert()
            mainViewModel.getRealmState().observe(this, {
                if (it) {
                    adapter.notifyDataSetChanged()
                }
            })
        }

        mainViewModel.getAllNotes().observe(this, {
            initRecyclerView(it)
        })

        buttonDeleteNote.setOnClickListener {
            mainViewModel.deleteAll().observe(this, {
                initRecyclerView(it)
            })
        }

    }

    private fun initRecyclerView(note: RealmResults<Notess>) {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = NoteAdapter()
        adapter.submitList(note)
        recycler_view.adapter = adapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Toast.makeText(baseContext, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

    }


}