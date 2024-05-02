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
import com.example.buddyapp.dataClass.accomodation_provider_Data
import com.google.firebase.database.FirebaseDatabase


class Accomodation_provider_Adaptore(private val context: Context, private val itmlstAccomodationP: ArrayList<accomodation_provider_Data>): RecyclerView.Adapter<Accomodation_provider_Adaptore.itmHolderAccomoP>() {
    class itmHolderAccomoP(itmView: View):RecyclerView.ViewHolder(itmView ){

        val itmnameAccomo: TextView =itmView.findViewById(R.id.tvNameACCOMODATION)
        val itmNicAccomo: TextView =itmView.findViewById(R.id.tvnicACCOMODATION)
        val itmAccomocontact: TextView =itmView.findViewById(R.id.tvContactACCOMODATION)
        val itmaAccomoddress: TextView =itmView.findViewById(R.id.tvAdressACCOMODATION)
        val itmAccomoimg: ImageView =itmView.findViewById(R.id.layoutImgACCOMODATION)
        val deleteA: ImageView =itmView.findViewById(R.id.deleteA)




    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmHolderAccomoP {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.acccomodation_provider_layout,parent,false)
        return itmHolderAccomoP(itemView)
    }


    override fun getItemCount(): Int {
        return  itmlstAccomodationP.size
    }

    override fun onBindViewHolder(holder:itmHolderAccomoP, position: Int) {

        val currentitem=itmlstAccomodationP[position]
        holder.itmnameAccomo.text = currentitem.itemNamAccomodation.toString()
        holder.itmNicAccomo.text = currentitem.itemNicAccomodation.toString()
        holder.itmAccomocontact.text = currentitem.itemContctAccomodation.toString()
        holder.itmaAccomoddress.text = currentitem.itemAdressAccomodation.toString()
        Glide.with(context).load(currentitem.itemImgeAccomodation).into(holder.itmAccomoimg)
//        val bytes=android.util.Base64.decode(currentitem.itemImgeAccomodation,android.util.Base64.DEFAULT)
//
//        val bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.size)
//        holder.itmAccomoimg.setImageBitmap(bitmap)



        //****************************************************************************************************************************
        //****************************************************************************************************************************
                                              //Deleting the Current User Data

       holder.deleteA.setOnClickListener {
            //Toast.makeText(context, "working", Toast.LENGTH_SHORT).show()
            var database = FirebaseDatabase.getInstance().getReference("itemAAcomodation").child(currentitem.push)
                .removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Item removed from the database, now update the local list
                        itmlstAccomodationP.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, itemCount)
                        Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }}}
