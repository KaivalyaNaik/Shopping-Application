package com.example.shoppingapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.repository.FirebaseRepository
import com.example.shoppingapplication.ui.LoginRegisterViewModel
import com.example.shoppingapplication.ui.LoginRegisterViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        val loginRegisterViewModel :LoginRegisterViewModel by viewModels {
            LoginRegisterViewModelFactory((application as ShoppingApplication).firebaseRepository)
        }
        loginRegisterViewModel.getUserLiveData().observe(this, Observer {
            if(it!=null){

                val intent:Intent =Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


        val emailText=findViewById<TextInputEditText>(R.id.editEmail)
        val password=findViewById<TextInputEditText>(R.id.editPassword)

        val loginButton:Button =findViewById(R.id.loginButton)
        val registerButton:Button=findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            Log.i("Register","register")
            loginRegisterViewModel.login(emailText.text.toString(),password.text.toString())
        }

        registerButton.setOnClickListener {
            Log.i("Register","register")
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}