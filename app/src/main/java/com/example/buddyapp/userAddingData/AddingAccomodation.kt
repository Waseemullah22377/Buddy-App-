package com.example.buddyapp.userAddingData

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.buddyapp.Screens.MainActivity
import com.example.buddyapp.dataClass.itemAccomodationData
import com.example.buddyapp.databinding.ActivityAddingAccomodationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class AddingAccomodation : AppCompatActivity() {
    var image:String?=""
    private lateinit var db2: DatabaseReference
    private lateinit var binding: ActivityAddingAccomodationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingAccomodationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addHotelData.setOnClickListener {
         insertDataAccomo()}}



    //*******************************************************************************************************
    //*******************************************************************************************************
    //*******************************************************************************************************
    //INSERTING DATA HERE




    fun insertDataAccomo() {
        val itemName = binding.AccomoName.text.toString()
        val itemAddress = binding.Accomoaddress.text.toString()
        val itemNIC = binding.AccomoNIC.text.toString()
        val itemContact = binding.Accomocontact.text.toString()


        //**************************************************************************************************
        //**************************************************************************************************
                                         //Constrain For Field

        if (itemName.isEmpty() || itemAddress.isEmpty()) {
            showError("Fields cannot be empty")
            return
        } else{


                                    //  Get the current user's UID
            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

            if(currentUserUid!= null) {

        db2 = FirebaseDatabase.getInstance().getReference("itemAAcomodation")
            val databaseRefrence = FirebaseDatabase.getInstance().reference
            val id = databaseRefrence.push().key
        val itemAccomo =id?.let{itemAccomodationData(itemName, itemAddress, itemContact, itemNIC, image,currentUserUid, id)}  //check
        db2.child(id.toString()).setValue(itemAccomo).addOnSuccessListener {

             clearFields()
            Toast.makeText(this, "data inserted", Toast.LENGTH_SHORT).show()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)

        }   } }

}


    private fun clearFields() {
        binding.AccomoName.text.clear()
        binding.Accomoaddress.text.clear()
        binding.Accomocontact.text.clear()
        binding.AccomoNIC.text.clear()
        image = ""
    }
    //*******************************************************************************************
    //*******************************************************************************************
    //*******************************************************************************************
    // INSERTING IMAGE HERE


    fun insertImage(view: View) {
        val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.type = "image/*"
        activityResultLauncher.launch(myfileintent)
    }

    private val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val bytes = stream.toByteArray()

                // Upload the image to Firebase Storage
                val storageRef = FirebaseStorage.getInstance().reference
                val imageRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")
                val uploadTask = imageRef.putFile(uri)


                uploadTask.addOnSuccessListener {
                    // Get the download URL and store it in your database
                    imageRef.downloadUrl.addOnSuccessListener {
                        image = it.toString()
                        binding.Accomoimg.setImageBitmap(myBitmap)
                        inputStream?.close()
                        Toast.makeText(this, "Image inserted", Toast.LENGTH_SHORT).show()

                    }
                                 }.addOnFailureListener { exception ->
                    Toast.makeText(this, "Image upload failed: ${exception.message}", Toast.LENGTH_LONG).show()
                }

            } catch (ex: Exception) {
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }}
//    fun insertImage(view: View) {
//        val myfileintent=Intent(Intent.ACTION_GET_CONTENT)
//        myfileintent.setType("image/*")
//        activityResultLauncher.launch(myfileintent)
//    }
//
//    @SuppressLint("SuspiciousIndentation")
//    private val activityResultLauncher=registerForActivityResult<Intent,ActivityResult>(
//        ActivityResultContracts.StartActivityForResult()
//    )
//
//    {result:ActivityResult->
//        if(result.resultCode== RESULT_OK){
//            val uri=result.data!!.data
//            try {
//                val inputStream=contentResolver.openInputStream(uri!!)
//                val myBitmap=BitmapFactory.decodeStream(inputStream)
//                val stream= ByteArrayOutputStream()
//                myBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
//                val bytes=stream.toByteArray()
//                image= Base64.encodeToString(bytes,Base64.DEFAULT)
//                binding.Accomoimg.setImageBitmap(myBitmap)
//                inputStream!!.close()
//                Toast.makeText(this,"image inserted",Toast.LENGTH_SHORT).show()
//
//
//            }catch (ex:Exception){
//                Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
//
//            }
//        }
//
//    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }}
