package com.example.buddyapp.Screens    //#EAE1E1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import com.example.buddyapp.R
import com.example.buddyapp.dataClass.signupData
import com.example.buddyapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.IOException

class SignUpScreen : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null
    private lateinit var firebaseStorage1: FirebaseStorage
    private lateinit var storageReference1: StorageReference
    private var storageRef = Firebase.storage
    private lateinit var mDatabaseRef: DatabaseReference

    //private var selectedIcon: ImageView? = null
    //private late init var imguri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseStorage1 = FirebaseStorage.getInstance()
        storageReference1 = FirebaseStorage.getInstance().reference
        auth = Firebase.auth
        storageRef = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("SignUpData")



        //**************************************************************************
        //**************************************************************************
        //**************************************************************************

       //code for hiding Password

        val passwordEditText = findViewById<EditText>(R.id.tvpassword)

        // Example: Retrieve the password when needed

       // val password = passwordEditText.text.toString()
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




        //**************************************************************************
        //**************************************************************************
        //**************************************************************************
        //CLICKING ON IMAGE VIEW

        binding.imageview.setOnClickListener {
            choosingimg() } }



    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //CODING FOR CHOOSING IMAGE


    private fun choosingimg(){

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST) }

    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {

            }
            if (data != null) {
                filePath = data.data
            }// !!
            try {
                val contentResolver = this.contentResolver
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.imageview.setImageBitmap(bitmap)



//*******************************************************************************************************
//*******************************************************************************************************
//*******************************************************************************************************

         // CLICKING ON REGISTER BUTTON

                binding.btnRegSigup.setOnClickListener {

                    // save  Userimage & data realtime data base
                    // usename and apassword save auth
                    val name = binding.Name.text.toString()
                    val address = binding.Tvaddress.text.toString()
                    val password = binding.tvpassword.text.toString()
                    val phonenumber = binding.phnNum.text.toString()
                    val email = binding.Tvaddress.text.toString()
                           saveImage()
                    // saveImage() // this fuction  save image storage db and Url realtime data base

                    //*******************************************************************************************************
                    //*******************************************************************************************************
                                      // edittext constrain

                    if (name.isEmpty()) {
                        Toast.makeText(this, "field is empty", Toast.LENGTH_SHORT).show()
                    } else if (email.isEmpty()) {
                        Toast.makeText(this, " field is  empty", Toast.LENGTH_SHORT).show()
                    } else if (password.isEmpty()) {
                        Toast.makeText(this, " field is empty", Toast.LENGTH_SHORT).show()
                    }
                  else if (phonenumber.isEmpty()) {
                        Toast.makeText(this, " field is  empty", Toast.LENGTH_SHORT).show()

                    } else if (address.isEmpty()){
                        Toast.makeText(this, " field is  empty", Toast.LENGTH_SHORT).show()

                    }else


                    //*******************************************************************************************************
                    //*******************************************************************************************************
                         // Data stored in firebase Authentication   email and password

                        auth.createUserWithEmailAndPassword(binding.Email.text.toString(), password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, "signup successfully", Toast.LENGTH_SHORT).show()
                                    val i = Intent(this,LonginScreen::class.java)
                                    startActivity(i)


                                  ///  val user = auth.currentUser
                                } else {

                                    Toast.makeText(this, "SignUp Failed${it.exception}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }

                }
                //**********************************
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }


    //*******************************************************************************************************
    //*******************************************************************************************************
    //*******************************************************************************************************

                       // save UserImage storage db & Url save realtime db

    private fun saveImage( ) {

        if (filePath != null) {
            val timestamp = System.currentTimeMillis().toString()
            val imageName = "imge_$timestamp"
            val imageRef =
                storageReference1.child("signup/${imageName}.jpg")    //stored in storage  ${imageName}
            // Set a unique image name

            val uploadTask = imageRef.putFile(filePath!!) // imageUri is the Uri of the image file

            uploadTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val name = binding.Name.text.toString()
                    //val password = binding.tvpassword.text.toString()
                   // val Re_password = binding.RePassword.text.toString()
                    val phoneNumber = binding.phnNum.text.toString()
                    val email = binding.Email.text.toString()
                    val address = binding.Tvaddress.text.toString()// issue place
                    //val profession = binding.profession.text.toString()
                    // Now you have the download URL of the uploaded image
                    Toast.makeText(this, "saved img in storage", Toast.LENGTH_SHORT)
                        .show()
                    saveImageUrlToDatabase(downloadUri.toString(),
                        name.toString(),
                        phoneNumber.toString(),
                        address.toString(),
                        email.toString(),


                    )
                } else {
                    // Handle failure
                    Toast.makeText(this, "not saved image in storage", Toast.LENGTH_SHORT).show()
                }
            }}}



    //*******************************************************************************************************
    //*******************************************************************************************************

                                  // code save image url realtime db

    private fun saveImageUrlToDatabase(image: String, name: String, phonenumber: String, address: String,email:String) {
        val user = signupData(name, image, email, phonenumber, address)
        val uid = auth.currentUser?.uid
        if (uid != null)
            mDatabaseRef.child(uid).setValue(user).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "save data & img db", Toast.LENGTH_SHORT).show()
                    binding.Name.text.clear()
                    binding.phnNum.text.clear()
                    binding.tvpassword.text.clear()
                    binding.Tvaddress.text.clear()
                    binding.Email.text.clear()

                    //binding.RePassword.text?.toString()
                }
            }
                .addOnFailureListener {
                    Toast.makeText(this, "not data save in db", Toast.LENGTH_SHORT).show() } }
}




