package com.example.shoppingapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.data.User
import com.example.shoppingapplication.ui.LoggedInViewModel
import com.example.shoppingapplication.ui.LoggedInViewModelFactory
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class ProfileActivity : AppCompatActivity() {

    val loggedInViewModel:LoggedInViewModel by viewModels {
        LoggedInViewModelFactory((application as ShoppingApplication).firebaseRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val name:TextView=findViewById(R.id.profileName)

        val email:TextView=findViewById(R.id.editProfileEmail)

        val company:TextView=findViewById(R.id.editProfileCompany)

        val dob:TextView=findViewById(R.id.editProfileDOB)

        val user: User? = loggedInViewModel.getUser().value

        val firebaseUser: FirebaseUser? =loggedInViewModel.getUserLiveData().value
        if(user!=null){
            name.text= user.name
            email.text=firebaseUser?.email.toString()
            company.text=user.company
            val date=SimpleDateFormat("dd/MM/yyyy").format(user.dateOfBirth)
            dob.text=date.toString()
        }

    }
}