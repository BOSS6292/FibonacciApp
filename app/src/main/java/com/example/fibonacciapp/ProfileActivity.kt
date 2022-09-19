package com.example.fibonacciapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.fibonacciapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //view Binding
    private lateinit var binding: ActivityProfileBinding

    //Action bar
    private lateinit var actionBar: ActionBar

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Action Bar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        binding.fibonacciGen.setOnClickListener {
            startActivity(Intent(this,FibonacciActivity::class.java))
        }
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null)
        {
            //user not null, user is logged in
            val email = firebaseUser.email
            //set to text view
            binding.emailTv.text = email
        }
        else
        {
            //user is null, user not logged in
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Please confirm.")
            setMessage("Are you want to exit the app?")

            setPositiveButton("Yes") { _, _ ->
                // if user press yes, then finish the current activity
                finish()
                super.onBackPressed()
            }

            setNegativeButton("No"){_, _ ->
                // if user press no, then return the activity
                Toast.makeText(this@ProfileActivity,"Thank You!!", Toast.LENGTH_SHORT).show()
            }

            setCancelable(true)
        }.create().show()
    }
}