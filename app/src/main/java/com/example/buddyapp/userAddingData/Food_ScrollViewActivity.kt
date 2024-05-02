package com.example.buddyapp.userAddingData

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.buddyapp.R
import com.example.buddyapp.databinding.ActivityFoodScrollViewBinding

class Food_ScrollViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityFoodScrollViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodScrollViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hashCode()

        val whatsappIconResourceId = R.drawable.whatsapp_icon_logo_svgrepo_com // Replace with the actual resource ID
        binding.DetailF.WhatsappBtn.setImageResource(whatsappIconResourceId)
//**********************************************************************************************************************
//**********************************************************************************************************************
                     //Getting Data
        val name = intent.getStringExtra("name")
        val experience = intent.getStringExtra("experience")
        val contact = intent.getStringExtra("contact")
        val address = intent.getStringExtra("Address")
        val image = intent.getStringExtra("image")

        Toast.makeText(this, "data get", Toast.LENGTH_SHORT).show()
//**********************************************************************************************************************
//**********************************************************************************************************************
                                  //Placing Data  on Text Field

        binding.DetailF.TvNameFood.text = name
        binding.DetailF.TvFoodEx.text = experience
        binding.DetailF.TvFoodCon.text = contact
        binding.DetailF.TvFoodAdd.text = address

        //**********************************************************************************************************************
//**********************************************************************************************************************
                              //Getting and placing image on Image view

        if (!image.isNullOrEmpty()) {

            // Using Glide to load the image into the ImageView
            Glide.with(this)
                .load(image)
                .into(binding.DetailF.FoodDImg)


            //**************************************************************************************************
            //*************************************************************************************************
            //*************************************************************************************************
                                        //CODING FOR BUTTON BOOKING

                                // Save the original background drawable and text
                               // val originalBackground =binding.Detail.book.background

                                     // R.drawable.tv_bg2
            val originalBackgroundResId = R.drawable.color_for_booing_button

            val originalText = binding.DetailF.TVFoodBook.text.toString()

            binding.DetailF.TVFoodBook.setOnClickListener(object : View.OnClickListener {
                var isClicked = false

                override fun onClick(view: View) {
                    // Change the background when the button is clicked
                    if (!isClicked) {
                        // binding.Detail.book.setBackgroundColor(resources.getColor(R.color.read))
                        binding.DetailF.TVFoodBook.setBackgroundColor(ContextCompat.getColor(this@Food_ScrollViewActivity, R.color.read))
                        binding.DetailF.TVFoodBook.text = "Booked"
                        Toast.makeText(this@Food_ScrollViewActivity,"You Reserved This Taxi",Toast.LENGTH_SHORT).show()
                    } else {
                        //binding.Detail.book.background = originalBackground
                        // Restore the original background when clicked again
                        if (originalBackgroundResId != null) {
                            binding.DetailF.TVFoodBook.setBackgroundResource(originalBackgroundResId)
                        }
                        binding.DetailF.TVFoodBook.text = originalText
                        Toast.makeText(this@Food_ScrollViewActivity,"You UnReserved This Taxi",Toast.LENGTH_SHORT).show()

                    }
                    // Toggle the click state
                    isClicked = !isClicked
                }
            })}


        //****************************************************************************
        //****************************************************************************
                   //setOnClickListener for WhatSapp button

        binding.DetailF.WhatsappBtn.setOnClickListener {

            if (contact != null) {
                openWhatsAppWithContact(contact)
            }
        }
            binding.DetailF.CallBtn.setOnClickListener {


                if (contact != null) {
                    openDialerWithNumber(contact)
                }

            }
        }

//******************************************************************************************************
//******************************************************************************************************
                        //MAKING FUNCTION FOR openWhatsAppWithContactnumber
        @SuppressLint("QueryPermissionsNeeded")//CHECK CHANGED
        private fun openWhatsAppWithContact(phoneNumber: String) {
            try {
                // Use the appropriate URI format for WhatsApp
                val uri = Uri.parse("smsto:$phoneNumber")

                // Create an Intent with the ACTION_SENDTO action
                val intent = Intent(Intent.ACTION_SENDTO, uri)

                // Set the package to "com.whatsapp" to ensure it opens in WhatsApp
                intent.setPackage("com.whatsapp")

                // Check if WhatsApp is installed on the device
                if (intent.resolveActivity(packageManager) != null) {
                    // Start the intent
                    startActivity(intent)
                } else {
                    // WhatsApp is not installed, you may want to handle this case
                    // For example, you can redirect the user to the WhatsApp download page
                    // or display a message indicating that WhatsApp is not installed.
                    Toast.makeText(this, "WhatsApp is not installed on this device.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle any exceptions that may occur
                e.printStackTrace()
            }

        }
    //******************************************************************************************************
    //******************************************************************************************************
                            //MAKING FUNCTION FOR openDialerWithNumber
        private fun openDialerWithNumber(phoneNumber: String) {
            try {
                // Use the "tel:" URI scheme to open the dialer with the specified number
                val uri = Uri.parse("tel:$phoneNumber")

                // Create an Intent with the ACTION_DIAL action
                val intent = Intent(Intent.ACTION_DIAL, uri)

                // Check if there's an activity that can handle the intent
                if (intent.resolveActivity(packageManager) != null) {
                    // Start the intent
                    startActivity(intent)
                } else {
                    // Handle the case where there's no activity to handle the intent
                    Toast.makeText(this, "No app can handle the dial action.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // Handle any exceptions that may occur
                e.printStackTrace()
            }
        }}

