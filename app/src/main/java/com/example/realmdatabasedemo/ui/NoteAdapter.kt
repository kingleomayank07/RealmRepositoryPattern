package com.example.realmdatabasedemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdatabasedemo.R
import com.example.realmdatabasedemo.data.Notess
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter : ListAdapter<Notess, NoteAdapter.NoteHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Notess>() {
            override fun areItemsTheSame(oldItem: Notess, newItem: Notess): Boolean {
                return oldItem.notesId == newItem.notesId
            }

            override fun areContentsTheSame(oldItem: Notess, newItem: Notess): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }


    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote: Notess = getItem(position)

        holder.textViewTitle.text = currentNote.title
        holder.textViewPriority.text = currentNote.priority.toString()
        holder.textViewDescription.text = currentNote.description
    }

    fun getNoteAt(position: Int): Notess {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
    }

    interface OnItemClickListener {
        fun onItemClick(note: Notess)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}