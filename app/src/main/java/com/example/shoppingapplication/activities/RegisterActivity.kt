package com.example.shoppingapplication.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.data.User
import com.example.shoppingapplication.repository.FirebaseRepository
import com.example.shoppingapplication.ui.LoginRegisterViewModel
import com.example.shoppingapplication.ui.LoginRegisterViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity:AppCompatActivity(){



    @RequiresApi(Build.VERSION_CODES.N)
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

        val email:EditText=findViewById(R.id.editRegisterEmail)

        val pasword:EditText=findViewById(R.id.editRegisterPassword)

        val name:EditText=findViewById(R.id.editName)

        val company:EditText=findViewById(R.id.editCompany)

        val stringdate: TextView =findViewById(R.id.editDate)

        stringdate.text="Tap select date of Birth"

        var cal = Calendar.getInstance()
        cal.add(Calendar.YEAR,-18)


        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            view.maxDate=cal.timeInMillis
            val myFormat = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            stringdate.text = sdf.format(cal.time)

        }

        stringdate.setOnClickListener {
            DatePickerDialog(this,dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)
                    ,cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        createButton.setOnClickListener {
            val date=SimpleDateFormat("dd-MM-yyyy").parse(stringdate.text.toString())
            val user= User(name.text.toString(),date,company.text.toString(),"")
            Log.d("Register","Register Button Pressed")
            loginRegisterViewModel.register(email.text.toString(),pasword.text.toString(),user)
        }
    }
}