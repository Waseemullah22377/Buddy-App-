package com.example.buddyapp.allAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buddyapp.R
import com.example.buddyapp.Screens.SignUpScreen
import com.example.buddyapp.dataClass.itemFoodData
import com.example.buddyapp.userAddingData.Food_ScrollViewActivity
import com.google.firebase.auth.FirebaseAuth



class FoodAdaptore(private val context: Context, private val itmlstFood: ArrayList<itemFoodData>): RecyclerView.Adapter<FoodAdaptore.itmHolderFood>() {
    class itmHolderFood(itmView: View): RecyclerView.ViewHolder(itmView ){

        val itmnameF: TextView =itmView.findViewById(R.id.tvnameF)
        val itmexpF: TextView =itmView.findViewById(R.id.tvexpF)
        val itmcontctF: TextView =itmView.findViewById(R.id.tvContactF)
        val itmaddressF: TextView =itmView.findViewById(R.id.tvAdressF)
        val itmimgF: ImageView =itmView.findViewById(R.id.layoutImgF)
        val foodviewbtn: TextView =itmView.findViewById(R.id.Food_ViewBtn)

    }

    class NotifyDataSetChanged {

    }

    //New changed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmHolderFood {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.food_layout,parent,false)
        return itmHolderFood(itemView)
    }


    override fun getItemCount(): Int {
        return  itmlstFood.size
    }

    override fun onBindViewHolder(holder:itmHolderFood, position: Int) {

        val currentitem=itmlstFood[position]
        holder.itmnameF.setText(currentitem.itemNameF.toString())
        holder.itmexpF.setText(currentitem.itemExpF.toString())
        holder.itmcontctF.setText(currentitem.itemContctF.toString())
        holder.itmaddressF.setText(currentitem.itemAdressF.toString())

        // Use Glide to load images efficiently
        if (!currentitem.itemImgeF.isNullOrEmpty()) {
            Glide.with(context)
                .load(currentitem.itemImgeF)
                .into(holder.itmimgF)
        }

        //************************************************************************************************************

        //  Coding of Constrain For  user and sending Data to Deatil Activity


        holder.foodviewbtn.setOnClickListener {

            if (isUserRegistered()) {
                // User is registered, go to detail activity
                val i = Intent(context, Food_ScrollViewActivity::class.java)
                i.putExtra("name",currentitem.itemNameF)
                i.putExtra("experience",currentitem.itemExpF)
                i.putExtra("contact",currentitem.itemContctF)
                i.putExtra("Address",currentitem.itemAdressF)
                i.putExtra("image",currentitem.itemImgeF)
                Toast.makeText(context, "Data Goes", Toast.LENGTH_SHORT).show()

                context.startActivity(i)

            } else {
                // User is not registered, go to signup activity
               val j = Intent(context, SignUpScreen::class.java)
                context.startActivity(j)
                Toast.makeText(context, "You Are Not Registered", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun  isUserRegistered(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }
}