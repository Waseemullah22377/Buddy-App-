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
import com.example.buddyapp.dataClass.itemAccomodationData
import com.example.buddyapp.userAddingData.Accomodation_ScrollViewActivity
import com.google.firebase.auth.FirebaseAuth



class AccomodationAdaptore(private val context: Context,private val itmlstAccomodation: ArrayList<itemAccomodationData>): RecyclerView.Adapter<AccomodationAdaptore.itmHolderAccomo>() {
    class itmHolderAccomo(itmView: View):RecyclerView.ViewHolder(itmView ){

        val itmnameAccomo: TextView =itmView.findViewById(R.id.tvNameACCOMODATION)
        val itmNicAccomo: TextView =itmView.findViewById(R.id.tvnicACCOMODATION)
        val itmAccomocontact: TextView =itmView.findViewById(R.id.tvContactACCOMODATION)
        val itmaAccomoddress: TextView =itmView.findViewById(R.id.tvAdressACCOMODATION)
        val itmAccomoimg: ImageView =itmView.findViewById(R.id.layoutImgACCOMODATION)
        val AccomoView: TextView =itmView.findViewById(R.id.Accomodation_Viewbtn)




    }
//************************************************new change
    class NotifyDataSetChanged {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmHolderAccomo {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.acccomodation_layout,parent,false)
        return itmHolderAccomo(itemView)
    }


    override fun getItemCount(): Int {
        return  itmlstAccomodation.size
    }

    override fun onBindViewHolder(holder:itmHolderAccomo, position: Int) {

        val currentitem=itmlstAccomodation[position]
        holder.itmnameAccomo.text = currentitem.itemNamAccomodation.toString()
        holder.itmNicAccomo.text = currentitem.itemNicAccomodation.toString()
        holder.itmAccomocontact.text = currentitem.itemContctAccomodation.toString()
        holder.itmaAccomoddress.text = currentitem.itemAdressAccomodation.toString()

        // Use Glide to load images efficiently
        if (!currentitem.itemImgeAccomodation.isNullOrEmpty()) {
            Glide.with(context)
                .load(currentitem.itemImgeAccomodation)
                .into(holder.itmAccomoimg)
        }


        //************************************************************************************************************

        //  Coding of Constrain For  user and sending Data to Deatil Activity


        holder.AccomoView.setOnClickListener {

            if (isUserRegistered()) {
                // User is registered, go to detail activity
                val i = Intent(context, Accomodation_ScrollViewActivity::class.java)
                i.putExtra("name",currentitem.itemNamAccomodation)
                i.putExtra("nic",currentitem.itemNicAccomodation)
                i.putExtra("contact",currentitem.itemContctAccomodation)
                i.putExtra("Address",currentitem.itemAdressAccomodation)
                i.putExtra("image",currentitem.itemImgeAccomodation)
              //  i.putExtra("imageReference", currentitem.itemImgeAccomodation) // Pass image reference


                Toast.makeText(context, "Data Goes", Toast.LENGTH_SHORT).show()

                context.startActivity(i)

            } else {
                // User is not registered, go to signup activity
                val j = Intent(context, SignUpScreen::class.java)
                context.startActivity(j)
                Toast.makeText(context, "You Are Not Registered", Toast.LENGTH_SHORT).show()
            }
        }
    }  fun  isUserRegistered(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null

    }
}