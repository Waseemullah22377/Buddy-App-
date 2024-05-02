package com.example.buddyapp.Screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import com.example.buddyapp.R
import com.example.buddyapp.databinding.ActivityloginBinding
import com.google.firebase.auth.FirebaseAuth

class LonginScreen : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth


               lateinit var binding:ActivityloginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityloginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        val sharedPrefs=this.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)



        //*******************************************************************************************************
        //*******************************************************************************************************
        //*******************************************************************************************************
                              //code for hidding Password

        val passwordEditText = findViewById<EditText>(R.id.password)

        // Example: Retrieve the password when needed

        val password = passwordEditText.text.toString()
        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()

        passwordEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= passwordEditText.right - passwordEditText.compoundDrawablesRelative[2].bounds.width()) {
                    // Toggle password visibility when the drawableEnd (eye icon) is clicked
                    if (passwordEditText.transformationMethod == null) {
                        // If it's null, it means the password is visible, so set it back to PasswordTransformationMethod
                        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                        passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.show_password_icon, 0)
                    } else {
                        // Otherwise, set it to null to make the password visible
                        passwordEditText.transformationMethod = null
                        passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.show_password_icon, 0)
                    }

                    // Move the cursor to the end of the text
                    passwordEditText.setSelection(passwordEditText.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }




        //*******************************************************************************************************
        //*******************************************************************************************************
        //*******************************************************************************************************
                        //CLICKING ON BUTTON LOGIN



        binding.btnlogin.setOnClickListener {

               val email=binding.email.text.toString()
              val password=binding.password.text.toString()

              if (email.isNotEmpty() && password.isNotEmpty()){
                  auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                      if (it.isSuccessful){

                          val editor=sharedPrefs.edit()
                          editor.putBoolean("isRegistered",true)
                          editor.apply()
                          Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show()
                          clearFields()
                          val i=Intent(this,MainActivity::class.java)
                          startActivity(i)
                      }else{
                          Toast.makeText(this,"Correct Your password",Toast.LENGTH_SHORT).show()
                      }
                  }


              }else{
                  Toast.makeText(this, "field cannot be empty", Toast.LENGTH_SHORT).show()
              }

          }
        //*******************************************************************************************************
        //*******************************************************************************************************
        //*******************************************************************************************************
                  //CODING FOR SIGNUP BUTTON

        binding.btnsinup.setOnClickListener {
            val j = Intent(this,SignUpScreen::class.java)
            startActivity(j)
        }
    }
    private fun clearFields() {
        binding.email.text.clear()
        binding.password.text.clear()}
}
