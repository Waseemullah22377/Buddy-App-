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
import com.example.buddyapp.dataClass.itemFoodData
import com.example.buddyapp.databinding.ActivityAddingFoodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class AddingFood : AppCompatActivity() {
    var image: String? = ""
    private lateinit var db3: DatabaseReference
    private lateinit var binding: ActivityAddingFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addFoodData.setOnClickListener {

            insertDataFood()
        }
    }



    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************

    // FUNCTION FOR CLEAR TEXT FIELD
    private fun clearFields() {
        binding.FoodName.text.clear()
        binding.FoodExpe.text.clear()
        binding.Foodcontact.text.clear()
        binding.Foodaddress.text.clear()
        image = ""
    }

    //*******************************************************************************************************
    //INSERTING DATA HERE


    fun insertDataFood() {
        val itemNameF = binding.FoodName.text.toString()
        val itemAddressF = binding.Foodaddress.text.toString()
        val itemExpF = binding.FoodExpe.text.toString()
        val itemContactF = binding.Foodcontact.text.toString()


        // CONSTRAIN ON FIELD
//        if (itemNameF.isEmpty()){
//            binding.FoodName.setError("field not be empty")
//            Toast.makeText(this,"Field is Empty",Toast.LENGTH_SHORT).show()
//        }else if (itemExpF.isEmpty()){
//            binding.FoodExpe.setError("field not be empty")
//        }else{

        if (itemNameF.isEmpty() || itemAddressF.isEmpty()) {
            showError("Fields cannot be empty")
            return
        } else {
            //  Get the current user's UID
            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

            if (currentUserUid != null) {

                db3 = FirebaseDatabase.getInstance().getReference("itemFood")
                val databaseRefrence = FirebaseDatabase.getInstance().reference
                val id = databaseRefrence.push().key

                val itemFood = id?.let { itemFoodData(itemNameF, itemAddressF, itemContactF, itemExpF, image, currentUserUid, it) }

                db3.child(id.toString()).setValue(itemFood).addOnSuccessListener {
                    clearFields()

                    Toast.makeText(this, "data inserted", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
            }
        }
    }

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
                        binding.Foodimg.setImageBitmap(myBitmap)
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


//        fun insertImage(view: View) {
//            val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
//            myfileintent.type = "image/*"
//            activityResultLauncher.launch(myfileintent)
//        }
//
//
//    @SuppressLint("SuspiciousIndentation")
//    private val activityResultLauncher: ActivityResultLauncher<Intent> =
//        registerForActivityResult<Intent, ActivityResult>(
//            ActivityResultContracts.StartActivityForResult()
//        )
//
//        { result: ActivityResult ->
//            if (result.resultCode == RESULT_OK) {
//                val uri = result.data!!.data
//                try {
//                    val inputStream = contentResolver.openInputStream(uri!!)
//                    val myBitmap = BitmapFactory.decodeStream(inputStream)
//                    val stream = ByteArrayOutputStream()
//                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                    val bytes = stream.toByteArray()
//                    image = Base64.encodeToString(bytes, Base64.DEFAULT)
//                    binding.Foodimg.setImageBitmap(myBitmap)
//                    inputStream!!.close()
//                    Toast.makeText(this, "image inserted", Toast.LENGTH_SHORT).show()
//
//
//                } catch (ex: Exception) {
//                    Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
//
//                }
//            }
//        }
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}







