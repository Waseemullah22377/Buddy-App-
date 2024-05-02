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
import com.example.buddyapp.dataClass.Food_provider_Data
import com.google.firebase.database.FirebaseDatabase


class Food_provider_Adaptore(private val context: Context, private val itmlstFoodF: ArrayList<Food_provider_Data>): RecyclerView.Adapter<Food_provider_Adaptore.itmHolderFoodF>() {
    class itmHolderFoodF(itmView: View): RecyclerView.ViewHolder(itmView ){

        val itmnameF: TextView =itmView.findViewById(R.id.tvnameF)
        val itmexpF: TextView =itmView.findViewById(R.id.tvexpF)
        val itmcontctF: TextView =itmView.findViewById(R.id.tvContactF)
        val itmaddressF: TextView =itmView.findViewById(R.id.tvAdressF)
        val itmimgF: ImageView =itmView.findViewById(R.id.layoutImgF)
        val deleteF: ImageView =itmView.findViewById(R.id.deleteF)

    }
    //New changed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmHolderFoodF {

        val itemViewF= LayoutInflater.from(parent.context).inflate(R.layout.food_provider_layout,parent,false)
        return itmHolderFoodF(itemViewF)
    }


    override fun getItemCount(): Int {
        return  itmlstFoodF.size
    }

    override fun onBindViewHolder(holder:itmHolderFoodF, position: Int) {

        val currentitem=itmlstFoodF[position]
        holder.itmnameF.setText(currentitem.itemNameF.toString())
        holder.itmexpF.setText(currentitem.itemExpF.toString())
        holder.itmcontctF.setText(currentitem.itemContctF.toString())
        holder.itmaddressF.setText(currentitem.itemAdressF.toString())

        Glide.with(context).load(currentitem.itemImgeF).into(holder.itmimgF)


//        val bytes=android.util.Base64.decode(current item.itemImgeF,android.util.Base64.DEFAULT)
//        val bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.size)
//        holder.itmimgF.setImageBitmap(bitmap)


        //****************************************************************************************************************************
        //****************************************************************************************************************************
        //Deleting the Current User Data

        holder.deleteF.setOnClickListener {
            //Toast.makeText(context, "working", Toast.LENGTH_SHORT).show()
            var database = FirebaseDatabase.getInstance().getReference("itemFood").child(currentitem.push).removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Item removed from the database, now update the local list
                        itmlstFoodF.removeAt(position)
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
