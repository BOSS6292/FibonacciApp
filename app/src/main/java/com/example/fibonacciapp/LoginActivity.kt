package com.example.fibonacciapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.fibonacciapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding

    //Action Bar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog:ProgressDialog

    //Firebase Auth
    private lateinit var firebaseAuth:FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure Action Bar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in....")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        //checkUser()

        //handle click, register activity
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        //handle click, begin login
        binding.loginBtn.setOnClickListener {
            //before logging in validate user data
            validateData()
        }

    }

    private fun validateData() {
        //getData
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid Email Format"
        }
        else if(TextUtils.isEmpty(password)){
            //no password Entered
            binding.passwordEt.error = "Please Enter password"
        }
        else
        {
            //data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()

                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this,FibonacciActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //login failure
                progressDialog.dismiss()
                Toast.makeText(this,"Login Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

   /* private fun checkUser() {
        //if user is already logged in go to profile activty
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null)
        {
            startActivity(Intent(this,FibonacciActivity::class.java))
            finish()
        }
    }*/
}