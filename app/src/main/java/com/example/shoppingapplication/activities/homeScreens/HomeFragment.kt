package com.example.shoppingapplication.activities.homeScreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.shoppingapplication.R
import com.example.shoppingapplication.activities.ItemList


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_home, container, false)
        val button=view.findViewById<Button>(R.id.nextPage)

        button.setOnClickListener {
            val intent= Intent(view.context, ItemList::class.java)
            startActivity(intent)
        }
        return view
    }

}