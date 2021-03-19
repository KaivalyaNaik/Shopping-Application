package com.example.shoppingapplication.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapplication.R
import com.example.shoppingapplication.data.Item
import com.example.shoppingapplication.repository.ItemDatabase
import com.example.shoppingapplication.repository.ItemRepository
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

class ItemListAdapter : ListAdapter<Item,ItemListAdapter.ItemViewHolder>(ItemComparator()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        
        val current =getItem(position)

        val coroutineContext=Dispatchers.Main
        val database=ItemDatabase.getDatabase(holder.itemView.context, CoroutineScope(coroutineContext))
        val repository=ItemRepository(database.itemDao())
        holder.bind(current.name,current.quantity,current.dateItemCreated,current.url)
        holder.itemView.editBtn.setOnClickListener {  }
        holder.itemView.deleteBtn.setOnClickListener {
           runBlocking {
               repository.delete(current)
           }
        }
    }

    class ItemViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){

        private val itemName :TextView =itemView.findViewById(R.id.item_name)
        private val itemQuantity:TextView =itemView.findViewById(R.id.item_quantity)
        private val itemDate:TextView =itemView.findViewById(R.id.item_date_added)
        private val itemImage:ImageView =itemView.findViewById(R.id.imageItem)

        fun bind(name:String?,quantity:Int?,date :String?,url:String?){

            if (name != null) {
                itemName.text=name.toUpperCase()
            }
            if (quantity!=null){
                itemQuantity.text=quantity.toString()
            }
            else{
                itemQuantity.text="Not Entered"
            }
            if(date!=null){
                itemDate.text=date.toString()
            }
            else{
                itemDate.text="Not registered"
            }
            if(url!=null){
                Glide.with(itemView.context).load(url).error(R.drawable.ic_baseline_error_outline_24).fitCenter().into(itemImage)
                Log.i("Glide",url)
            }


        }


        companion object{
            fun create(parent: ViewGroup):ItemViewHolder{
                val view:View =LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
                return ItemViewHolder(view)
            }
        }
    }
    class ItemComparator: DiffUtil.ItemCallback<Item>() {


        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id==newItem.id
        }
    }




}