package com.example.buddyapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buddyapp.R
import com.example.buddyapp.allAdapter.AccomodationAdaptore
import com.example.buddyapp.dataClass.itemAccomodationData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccomodationFragment : Fragment(R.layout.accomodation_frgment) {
    private lateinit var db2: DatabaseReference
    private lateinit var itemRecyclerViewAccomo: RecyclerView
    private lateinit var itemArrlistAccomo: ArrayList<itemAccomodationData>

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemRecyclerViewAccomo = view.findViewById(R.id.RV2)!!


        val itemRecyclerView = view.findViewById<RecyclerView>(R.id.RV2)
        itemRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        itemRecyclerView?.hasFixedSize()
        itemArrlistAccomo = arrayListOf<itemAccomodationData>()


    }

    private fun getItemDataa() {
        db2 = FirebaseDatabase.getInstance().getReference("itemAAcomodation")
        db2.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                itemArrlistAccomo.clear()
                if (snapshot.exists()) {
                    for (itmsnapshot in snapshot.children) {
                        val itemAccomo = itmsnapshot.getValue(itemAccomodationData::class.java)
                        itemArrlistAccomo.add(itemAccomo!!)
                    }
                    //****************************************// new
                    // Notify the adapter that the data has changed
                    // Also, instantiate the adapter only if the fragment is attached to the activity
                    if (isAdded) {
                        val adapter = context?.let { AccomodationAdaptore(it, itemArrlistAccomo) }
                        itemRecyclerViewAccomo.adapter = adapter
                        adapter?.notifyDataSetChanged()
                    }

                    //AccomodationAdaptore.NotifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_LONG).show()
                }
              //  itemRecyclerViewAccomo.adapter =
               //     context?.let { AccomodationAdaptore(it, itemArrlistAccomo) }// new changed

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        itemArrlistAccomo.clear()
        getItemDataa()
        AccomodationAdaptore.NotifyDataSetChanged()

    }

}



