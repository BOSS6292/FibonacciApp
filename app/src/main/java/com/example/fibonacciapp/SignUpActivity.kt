 package com.example.fibonacciapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.fibonacciapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

 class SignUpActivity : AppCompatActivity() {
    //View Binding
    private lateinit var binding: ActivitySignUpBinding

    //Action Bar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
     private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Action Bar //enabled Back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account please wait....")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click and begin sign up
        binding.signUpBtn.setOnClickListener {
            //validate data
            validateData()
        }
    }

     private fun validateData() {
         //get Data
          email = binding.emailEt.text.toString().trim()
         password = binding.passwordEt.text.toString().trim()

         //validate data
         if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             //invalid email format
             binding.emailEt.error = "Invalid email format"
         }
         else if(TextUtils.isEmpty(password)){
             binding.passwordEt.error = "Please enter password"
         }
         else if(password.length<6)
         {
             binding.passwordEt.error = "Password Length should be above 6 digits"
         }
         else
         {
             //data is valid, continue signup
             firebaseSignUp()
         }
     }

     private fun firebaseSignUp() {
         //show progress
         progressDialog.show()

         //create acc
         firebaseAuth.createUserWithEmailAndPassword(email,password)
             .addOnSuccessListener {
                 progressDialog.dismiss()
                 //get Current User
                 val firebaseUser = firebaseAuth.currentUser
                 val email = firebaseUser!!.email
                 Toast.makeText(this,"Account created with email $email",Toast.LENGTH_SHORT).show()

                 //openProfile
                 startActivity(Intent(this,ProfileActivity::class.java))
                 finish()
             }
             .addOnFailureListener {  e->
                 progressDialog.dismiss()
                 Toast.makeText(this,"SignUp failed due to ${e.message}",Toast.LENGTH_SHORT).show()
             }
     }

     override fun onSupportNavigateUp(): Boolean {
         onBackPressed() //go back to previous activity, when back button of actionbar is clicked
         return super.onSupportNavigateUp()
     }
}