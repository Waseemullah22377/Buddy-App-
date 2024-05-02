package com.example.buddyapp.userAddingData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.buddyapp.databinding.ActivityAddingAccomodationBinding
import com.example.buddyapp.databinding.ActivityDetailBinding

class Acccomodation_Detail_Activity : AppCompatActivity() {
    lateinit var binding:ActivityAddingAccomodationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddingAccomodationBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}