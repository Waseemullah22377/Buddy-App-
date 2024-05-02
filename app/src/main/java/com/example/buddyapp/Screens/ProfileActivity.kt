package com.example.buddyapp.Screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.buddyapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference =
            userId?.let { FirebaseDatabase.getInstance().reference.child("SignUpData").child(it) }


        //*******************************************************************************************************
        //*******************************************************************************************************
        //*******************************************************************************************************
                       // Retrieve  Userimage URL & data  from the Realtime Database

        databaseReference?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val imageUrl = dataSnapshot.child("image").value.toString()
                    val name = dataSnapshot.child("name").value.toString()
                    val email = dataSnapshot.child("email").value.toString()
                    val pohonenumber = dataSnapshot.child("phone-number").value.toString()
                    val address = dataSnapshot.child("address").value.toString()


                                //GIVING DATA TO PROFILE VIEWS

                    binding.EmailP.text = email
                    binding.Name.text = name
                    binding.TvaddressP.text = address
                    binding.phnNumP.text = pohonenumber

                    // Load and display the image using Glide
                    Glide.with(this@ProfileActivity)
                        .load(imageUrl)
                        .into(binding.imageviewP)


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })


        //*******************************************************************************************************
        //*******************************************************************************************************
        //*******************************************************************************************************

    }
    //*******************************************************************************************************
    //*******************************************************************************************************
    //*******************************************************************************************************

}

