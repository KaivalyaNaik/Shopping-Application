package com.example.shoppingapplication.activities

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.data.Item
import com.example.shoppingapplication.ui.ItemListAdapter
import com.example.shoppingapplication.ui.ItemViewModel
import com.example.shoppingapplication.ui.ItemViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_item_list.view.*

class ItemList : AppCompatActivity() {
    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModelFactory((application as ShoppingApplication).repository)
    }

    private val newItemActivityRequestCode =1

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)



        val root=findViewById<View>(R.id.item_list)


        val appBarLayout =findViewById<AppBarLayout>(R.id.app_bar)

        val recyclerView= findViewById<RecyclerView>(R.id.recyclerview)
        val adapter= ItemListAdapter()
        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(this)

        itemViewModel.allItems.observe(this, Observer {    items->
            items?.let { adapter.submitList(it) }
        })

        val fab=findViewById<FloatingActionButton>(R.id.fab)
        var dX:Float=0F
        var dY:Float=0F
        root.setOnDragListener { v, event ->

            when(event.action){
                DragEvent.ACTION_DRAG_LOCATION->{
                    dX=event.x
                    dY=event.y
                }
                DragEvent.ACTION_DRAG_ENDED->{
                    fab.x=(dX-fab.width/2)
                    fab.y=dY-fab.width/2
                }
            }
            true
        }
        fab.setOnClickListener {
            val intent = Intent(this, NewItem::class.java)
            startActivityForResult(intent, newItemActivityRequestCode)
        }
        fab.setOnLongClickListener {
            val myShadow= View.DragShadowBuilder(fab)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                it.startDragAndDrop(null,myShadow,null,View.DRAG_FLAG_GLOBAL)
            else
                it.startDrag(null,myShadow,null,View.DRAG_FLAG_GLOBAL)
            true
        }
       /* appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener{
            appBarLayout, verticalOffset ->
            Log.d("vertical offset",appBarLayout.totalScrollRange.toString())

            appBarLayout.imageView2.alpha=((appBarLayout.imageView2.alpha-0)/341)*1  +0
        })
*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var name:String =""
        var quantity:Int=0
        var url:String =""

        if(requestCode ==newItemActivityRequestCode &&resultCode == Activity.RESULT_OK){
            if(data ==null)
                print("onActivityResult"+"data is null")
            data?.getStringExtra("name")?.let {
                name=it
            }
            data?.getStringExtra("quantity")?.let {
                quantity=it.toInt()
            }
            data?.getStringExtra("url")?.let {
                url=it

            }
            val item = Item(
                name = name,
                quantity = quantity,
                url = url

            )
            itemViewModel.insert(item)
            val layout=findViewById<View>(R.id.item_list)
            Snackbar.make(layout,"Item Added Successfully",3000).show()

        }
        else{
            Toast.makeText(applicationContext,"Item Not Added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateAppbarView(offset:Float){

    }


}