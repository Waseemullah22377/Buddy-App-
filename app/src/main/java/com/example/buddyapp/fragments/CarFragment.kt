package com.example.buddyapp.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buddyapp.R
import com.example.buddyapp.allAdapter.CarAdaptore
import com.example.buddyapp.dataClass.itemCarData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CarFragment:Fragment(R.layout.car_frgment) {
    private lateinit var db: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrlist: ArrayList<itemCarData>
    private lateinit var carAdapter: CarAdaptore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemRecyclerView = view.findViewById(R.id.recyclerV)!!

        // *****************************************************************************************************
        // *****************************************************************************************************

        val itemRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerV)
        itemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemRecyclerView?.hasFixedSize()
        itemArrlist = arrayListOf<itemCarData>()

      carAdapter = CarAdaptore(requireContext(), itemArrlist)
      itemRecyclerView.adapter = carAdapter

    }
    private fun getItemDataa() {
        db = FirebaseDatabase.getInstance().getReference("carData")
        db.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                itemArrlist.clear()
                if (snapshot.exists()) {
                    for (itmsnapshot in snapshot.children) {
                        val itemcar = itmsnapshot.getValue(itemCarData::class.java)
                        if (itemcar != null) { //new changed
                        itemArrlist.add(itemcar!!)}
                    }
                    carAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(),"No Data Found", Toast.LENGTH_LONG).show()
                }
                }
                    override fun onCancelled(error: DatabaseError) {

                    }
        })
    }
   @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        itemArrlist.clear()
        getItemDataa()
        carAdapter.notifyDataSetChanged()

    }

}




