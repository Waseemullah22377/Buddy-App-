package com.example.buddyapp.userAddingData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buddyapp.databinding.ActivityFoodDetailBinding

class Food_Detail_Activity : AppCompatActivity() {
    lateinit var binding:ActivityFoodDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}