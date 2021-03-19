package com.example.shoppingapplication.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ItemList : AppCompatActivity() ,View.OnTouchListener{
    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModelFactory((application as ShoppingApplication).repository)
    }

    private val newItemActivityRequestCode =1

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val root=findViewById<View>(R.id.item_list)

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
            it.startDragAndDrop(null,myShadow,null,View.DRAG_FLAG_GLOBAL)
            true
        }

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

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {


        if (event != null && v!=null) {
            var dx:Float = 0F
            var dy:Float =0F
            var lastAction:Int=0

            when(event.actionMasked){
                MotionEvent.ACTION_DOWN -> {
                    dx = v.x -event.rawX
                    dy =v.y -event.rawY
                    lastAction =MotionEvent.ACTION_DOWN
                }
                MotionEvent.ACTION_MOVE->{
                    val newX=dx +event.rawX
                    val newY=dy +event.rawY

                    v.animate().x(newX).y(newY).setDuration(0).start()
                    lastAction=MotionEvent.ACTION_MOVE
                }
                MotionEvent.ACTION_UP->{
                    val x=event.rawX
                    val y=event.rawY

                    val upX=x-dx
                    val upY=y-dy

                    if(Math.abs(upX) <1 && Math.abs(upY)<1) {
                        val intent = Intent(this, NewItem::class.java)
                        startActivityForResult(intent, newItemActivityRequestCode)
                        return false
                    }
                    else
                        return false
                }
                else->{
                    return super.onTouchEvent(event)
                }


            }
        }
        return true
    }
}