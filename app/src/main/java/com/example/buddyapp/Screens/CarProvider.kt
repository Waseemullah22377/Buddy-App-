package com.example.buddyapp.Screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buddyapp.R
import com.example.buddyapp.allAdapter.CarproviderAdapter
import com.example.buddyapp.dataClass.providerCarData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CarProvider : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var adapter: CarproviderAdapter
    private lateinit var itemArrlist2: ArrayList<providerCarData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_provider)
        // Initialize RecyclerView**
        itemRecyclerView = findViewById(R.id.carProviderRecy)
        //new    
        itemArrlist2 = ArrayList()
        ////////////////////////**

        adapter = CarproviderAdapter(this, itemArrlist2)
        itemRecyclerView.adapter = adapter
        val currentUser = FirebaseAuth.getInstance().currentUser

        //******************************************************************************************************************************
        // Add spacing between items in the RecyclerView

        itemRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val spacingInPixels =
            resources.getDimensionPixelSize(R.dimen.spacing_between_items) // Adjust the dimension as needed
        itemRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels))

        loadcurrentuserData()
    }

    private fun loadcurrentuserData() {
        val currentuser = FirebaseAuth.getInstance().currentUser
        val userId = currentuser?.uid
        val databaseRef = FirebaseDatabase.getInstance().reference.child("carData")

        if (userId != null) {
            itemArrlist2.clear()

            // Use query to filter data for the current user
            val query = databaseRef.orderByChild("currentUserUid").equalTo(userId)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        if (dataSnapshot.hasChildren()) {
                            val carData = dataSnapshot.getValue(providerCarData::class.java)
                            carData?.let {
                                itemArrlist2.add(it)
                                // Toast.makeText(this@CarProvider, "its fine", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    // Notify adapter after the loop is done
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled
                }
            })
        }
    }
}
