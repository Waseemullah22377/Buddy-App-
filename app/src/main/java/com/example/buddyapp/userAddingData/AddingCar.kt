package com.example.buddyapp.userAddingData

import android.annotation.SuppressLint
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
import com.example.buddyapp.dataClass.itemCarData
import com.example.buddyapp.databinding.ActivityAddingCarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class AddingCar : AppCompatActivity() {
    private lateinit var db: DatabaseReference

    private val storage = FirebaseStorage.getInstance()
    private lateinit var image: String

    private lateinit var binding: ActivityAddingCarBinding

    //#CDD5D3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingCarBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //*****************************************************************************************************************************************
        //*****************************************************************************************************************************************

                                            // setOnClickListener  For Adding Daata


        binding.btnadd.setOnClickListener {   insertData() }
    }



    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************

                                            // INSERTING DATA FUNCTION
    @SuppressLint("SuspiciousIndentation")
    fun insertData() {
        val itemName = binding.drivrN.text.toString()
        val itemCarModel = binding.carmodl.text.toString()
        val itemContact = binding.contact.text.toString()
        val itemAddress = binding.address.text.toString()


        //*****************************************************************************************************************************************
        //*****************************************************************************************************************************************

                                              // Validation For Text Field

        if (itemName.isEmpty() || itemCarModel.isEmpty()) {
            showError("Fields cannot be empty")
            return
        } else{
                        //  Get the current user's UID
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

       if(currentUserUid!= null) {

            db = FirebaseDatabase.getInstance().getReference("carData")
            val databaseRefrence = FirebaseDatabase.getInstance().reference
           val id = databaseRefrence.push().key
            val item = image.let { itemCarData(itemName, itemAddress, itemContact, itemCarModel,it,currentUserUid,id) }


                db.child(id.toString()).setValue(item).addOnSuccessListener {
                    clearFields()
                    Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                    val w = Intent(this, MainActivity::class.java)
                    startActivity(w)
                } }}}



    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************

                                              // FUNCTION FOR CLEAR TEXT FIELD
    private fun clearFields() {
        binding.drivrN.text.clear()
        binding.address.text.clear()
        binding.contact.text.clear()
        binding.carmodl.text.clear()
        image = ""
    }



    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************
                                                    // INSERTING IMAGE
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
                        binding.img.setImageBitmap(myBitmap)
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
//        val myfileintent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
//        activityResultLauncher.launch(myfileintent)
//    }
//
//    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == RESULT_OK) {
//            result.data?.data?.let { uri ->
//                try {
//                    contentResolver.openInputStream(uri)?.use { inputStream ->
//                        val myBitmap = BitmapFactory.decodeStream(inputStream)
//                        val bytes = ByteArrayOutputStream().apply {
//                            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
//                        }.toByteArray()
//                        image = Base64.encodeToString(bytes, Base64.DEFAULT)
//                        binding.img.setImageBitmap(myBitmap)
//                        // Upload image to Firebase Storage
//                        //Toast.makeText(this, "Image inserted", Toast.LENGTH_SHORT).show()
//                    }
//                } catch (ex: Exception) {
//                    Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}