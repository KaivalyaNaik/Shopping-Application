package com.example.shoppingapplication.ui

import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapplication.data.Item

import kotlinx.android.synthetic.main.infinite_list.*

class PaginationAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items :MutableList<Item> = mutableListOf()
    private  var isLoadingAdded= false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return items.size
    }


}