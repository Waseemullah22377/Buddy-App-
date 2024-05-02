package com.example.buddyapp.allAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buddyapp.R
import com.example.buddyapp.dataClass.providerCarData
import com.google.firebase.database.FirebaseDatabase


class CarproviderAdapter ( private val context: Context, private val itmlst2: ArrayList<providerCarData>):
    RecyclerView.Adapter<CarproviderAdapter.itmHolder2>() {


    class itmHolder2(itmView: View) : RecyclerView.ViewHolder(itmView) {


        val itmname: TextView = itmView.findViewById(R.id.tvName)
        val itmmodl: TextView = itmView.findViewById(R.id.tvModel)
        val itmcontact: TextView = itmView.findViewById(R.id.tvContact)
        val itmaddress: TextView = itmView.findViewById(R.id.tvAdress)
        val itmimg: ImageView = itmView.findViewById(R.id.layoutImg)

        val delete: ImageView = itemView.findViewById(R.id.delete) }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmHolder2 {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.carprovider_layout, parent, false)
        return itmHolder2(itemView) }


    override fun getItemCount(): Int { return itmlst2.size }

    override fun onBindViewHolder(holder: itmHolder2, position: Int) {
        val currentitem = itmlst2[position]

        holder.itmname.text = currentitem.itemNam.toString()
        holder.itmmodl.text = currentitem.itemMdel.toString()
        holder.itmcontact.text = currentitem.itemContct.toString()
        holder.itmaddress.text = currentitem.itemAdress

        Glide.with(context).load(currentitem.itemImge).into(holder.itmimg)


        //****************************************************************************************************************************
        //****************************************************************************************************************************
                             //Deleting the Current User Data

        holder.delete.setOnClickListener {

            var database = FirebaseDatabase.getInstance().getReference("carData").child(currentitem.push)
                    .removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            // Item removed from the database, now update the local list

                            itmlst2.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, itemCount)
                            //****************************************************************************************************************************
                            //****************************************************************************************************************************
                                                          //  Toast For Item deleted Successfully
                            Toast.makeText(context, "Item deleted Successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            //****************************************************************************************************************************
                            //****************************************************************************************************************************
                                            //  Toast For Failed to delete item
                            Toast.makeText(context, "Failed to delete Item", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
        }}}
