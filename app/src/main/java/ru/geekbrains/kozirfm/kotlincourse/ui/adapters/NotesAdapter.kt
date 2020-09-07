package ru.geekbrains.kozirfm.kotlincourse.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_note.view.*
import ru.geekbrains.kozirfm.kotlincourse.R
import ru.geekbrains.kozirfm.kotlincourse.data.entity.Note

class NotesAdapter(var onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.cell_note, parent, false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.count()
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            txtTitleNote.text = note.title
            txtTextNote.text = note.text

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }
}
