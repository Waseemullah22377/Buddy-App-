package com.example.buddyapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buddyapp.R
import com.example.buddyapp.allAdapter.FoodAdaptore
import com.example.buddyapp.dataClass.itemFoodData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodFragment : Fragment(R.layout.food_frgment) {

    private lateinit var db3: DatabaseReference
    private lateinit var itemRecyclerViewFood: RecyclerView
    private lateinit var itemArrlistFood: ArrayList<itemFoodData>

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemRecyclerViewFood = view.findViewById(R.id.RV3)!!


        val itemRecyclerView = view.findViewById<RecyclerView>(R.id.RV3)
        itemRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        itemRecyclerView?.hasFixedSize()
        itemArrlistFood = arrayListOf<itemFoodData>()


    }

    private fun getItemDataa() {
        itemArrlistFood.clear()
        db3 = FirebaseDatabase.getInstance().getReference("itemFood")
        db3.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itmsnapshot in snapshot.children) {
                        val itemFood = itmsnapshot.getValue(itemFoodData::class.java)
                        itemArrlistFood.add(itemFood!!)
                    }
                    // FoodAdaptore.NotifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_LONG).show()
                }
                // itemRecyclerViewFood.adapter= FoodAdaptore(itemArrlistFood)
                itemRecyclerViewFood.adapter =
                    context?.let { FoodAdaptore(it, itemArrlistFood) }// new changed


            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        itemArrlistFood.clear()
        getItemDataa()
        FoodAdaptore.NotifyDataSetChanged()
    }


}