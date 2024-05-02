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
import com.example.buddyapp.ScrollViewActivity
import com.example.buddyapp.dataClass.itemCarData
import com.google.firebase.auth.FirebaseAuth

class CarAdaptore(private val context: Context, private val itmlst: ArrayList<itemCarData>):RecyclerView.Adapter<CarAdaptore.itmHolder>() {
    class itmHolder(itmView:View):RecyclerView.ViewHolder(itmView ){



        val itmname:TextView=itmView.findViewById(R.id.tvName)
        val itmmodl:TextView=itmView.findViewById(R.id.tvModel)
        val itmcontact:TextView=itmView.findViewById(R.id.tvContact)
        val itmaddress:TextView=itmView.findViewById(R.id.tvAdress)
        val itmimg:ImageView=itmView.findViewById(R.id.layoutImg)
        val view:TextView=itmView.findViewById(R.id.View)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmHolder {


        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.car_layout,parent,false)
        return  itmHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  itmlst.size
    }

    override fun onBindViewHolder(holder: itmHolder, position: Int) {

        val currentitem=itmlst[position]

        holder.itmname.setText(currentitem.itemNam.toString())
        holder.itmmodl.setText(currentitem.itemMdel.toString())
        holder.itmcontact.setText(currentitem.itemContct.toString())
        holder.itmaddress.setText(currentitem.itemAdress)
        holder.itmname.text = currentitem.itemNam ?: "Default Name"

        // Use Glide to load images efficiently
        if (!currentitem.itemImge.isNullOrEmpty()) {
            Glide.with(context)
                .load(currentitem.itemImge)
                .into(holder.itmimg)
        }



        //************************************************************************************************************
        //************************************************************************************************************

                       //  Coding of Constrain For  user and sending Data to Deatil Activity


          holder.view.setOnClickListener {


              if (isUserRegistered()) {
                  // User is registered, go to detail activity
                  val i = Intent(context, ScrollViewActivity::class.java)
                  i.putExtra("name",currentitem.itemNam)
                  i.putExtra("Address",currentitem.itemAdress)
                  i.putExtra("contact",currentitem.itemContct)
                  i.putExtra("model",currentitem.itemMdel)

                  i.putExtra("image",currentitem.itemImge)
                  Toast.makeText(context, "Data Goes", Toast.LENGTH_SHORT).show()

                  context.startActivity(i)


              } else {
                  // User is not registered, go to signup activity
                  val j = Intent(context, SignUpScreen::class.java)
                  context.startActivity(j)
                  Toast.makeText(context, "You Are Not Registered", Toast.LENGTH_SHORT).show()
              }
          }

        //************************************************************************************************************
        //************************************************************************************************************
                        //Function For User Verification
    }  fun  isUserRegistered(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }}
