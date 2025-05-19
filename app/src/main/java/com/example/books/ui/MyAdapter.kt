package com.example.books.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MyAdapter : ListAdapter<Any, MyAdapter.VH>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.text.text = when(item) {
            is com.example.books.data.Book -> item.title
            is com.example.books.data.Genre -> item.name
            else -> item.toString()
        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(android.R.id.text1)
    }

    class DiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(a: Any, b: Any) =
            (a is com.example.books.data.Book && b is com.example.books.data.Book &&
                    a.bookId == b.bookId)
                    || (a is com.example.books.data.Genre && b is com.example.books.data.Genre &&
                    a.genreId == b.genreId)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(a: Any, b: Any) = a == b
    }
}