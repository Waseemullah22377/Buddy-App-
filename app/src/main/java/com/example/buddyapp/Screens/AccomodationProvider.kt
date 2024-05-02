package com.example.buddyapp.Screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buddyapp.R
import com.example.buddyapp.allAdapter.Accomodation_provider_Adaptore
import com.example.buddyapp.dataClass.accomodation_provider_Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccomodationProvider() : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var adapter: Accomodation_provider_Adaptore
    private lateinit var itemArrlistP: ArrayList<accomodation_provider_Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accomodation_provider)
        // Initialize RecyclerView**
        itemRecyclerView = findViewById(R.id.accomodationProviderRecy)
        //new    
        itemArrlistP = ArrayList()
        ////////////////////////**

        adapter = Accomodation_provider_Adaptore(this, itemArrlistP)
        itemRecyclerView.adapter = adapter
        val currentUser = FirebaseAuth.getInstance().currentUser
        itemRecyclerView.layoutManager = GridLayoutManager(this, 2)
        // Add spacing between items in the RecyclerView
        val spacingInPixels =
            resources.getDimensionPixelSize(R.dimen.spacing_between_items) // Adjust the dimension as needed
        itemRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels))

        loadcurrentuserData()
    }

    private fun loadcurrentuserData() {
        val currentuser = FirebaseAuth.getInstance().currentUser
        val userId = currentuser?.uid
        val databaseRef = FirebaseDatabase.getInstance().reference.child("itemAAcomodation")

        if (userId != null) {
            itemArrlistP.clear()

            // Use query to filter data for the current user
            val query = databaseRef.orderByChild("currentUserUid").equalTo(userId)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        if (dataSnapshot.hasChildren()) {
                            val accomodationData =
                                dataSnapshot.getValue(accomodation_provider_Data::class.java)
                            accomodationData?.let {
                                itemArrlistP.add(it)
                                // Toast.makeText(this@YourActivity, "It's fine", Toast.LENGTH_SHORT).show()
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
