package com.example.shoppingapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.repository.FirebaseRepository
import com.example.shoppingapplication.ui.LoginRegisterViewModel
import com.example.shoppingapplication.ui.LoginRegisterViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity:AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(
                R.layout.activity_register
        )

       val loginRegisterViewModel:LoginRegisterViewModel by viewModels<LoginRegisterViewModel> {
           LoginRegisterViewModelFactory((application as ShoppingApplication).firebaseRepository)
       }

        loginRegisterViewModel.getUserLiveData().observe(this, Observer {
            if(it!=null){
                val intent:Intent =Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        val createButton:Button=findViewById(R.id.createAccount)

        val email:TextInputEditText=findViewById(R.id.editRegisterEmail)

        val pasword:TextInputEditText=findViewById(R.id.editRegisterPassword)

        val name:TextInputEditText=findViewById(R.id.editName)


        createButton.setOnClickListener {
            loginRegisterViewModel.register(email.text.toString(),pasword.text.toString(),name.text.toString())
        }
    }
}