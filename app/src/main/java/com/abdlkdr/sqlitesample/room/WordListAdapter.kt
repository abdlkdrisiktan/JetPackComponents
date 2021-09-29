package com.abdlkdr.sqlitesample.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdlkdr.sqlitesample.R

/**
 * Created by aisiktan on 27,September,2021
 */
class WordListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class WordViewHolder(itemView: View, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        fun bind(word: Word) {
            wordItemView.text = word.word
            wordItemView.setOnClickListener {
                listener.onItemClick(it, adapterPosition, word)
            }
            wordItemView.setOnLongClickListener {
                listener.onLongItemClick(it,adapterPosition, word)
                true
            }
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickListener): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.word_recyclerview_item, parent, false)
                return WordViewHolder(view, listener)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, word: Word)
        fun onLongItemClick(view: View, position: Int, word: Word)
    }
}