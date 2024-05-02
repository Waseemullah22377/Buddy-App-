package com.example.buddyapp.Screens

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.buddyapp.R
import com.example.buddyapp.databinding.ActivityMainBinding
import com.example.buddyapp.fragments.AccomodationFragment
import com.example.buddyapp.fragments.CarFragment
import com.example.buddyapp.fragments.FoodFragment
import com.example.buddyapp.userAddingData.AddingAccomodation
import com.example.buddyapp.userAddingData.AddingCar
import com.example.buddyapp.userAddingData.AddingFood
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private  lateinit var drawerLayout: DrawerLayout
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navigation = findViewById<NavigationView>(R.id.navID)
         drawerLayout= findViewById(R.id.drawerl)


        //******************************************************************************************************
        //*******************************************************************************************************
                           //DRAWER CODING



        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
         supportActionBar!!.setHomeButtonEnabled(true)
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigation?.bringToFront()

        //*******************************************************************************************************
        //*******************************************************************************************************
                              //CLICK ON EACH ITEM

        navigation?.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_profile -> {
                    if(isUserRegistered()){
                    //Toast.makeText(this, " you clicked nav profile", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "You Are Not Registred", Toast.LENGTH_SHORT).show()
                        val i = Intent(this, SignUpScreen::class.java)
                        startActivity(i)

                    }
                }

                //************************************************************************************************************
                //*******************************************************************************************************
                //*******************************************************************************************************
                                         //SHOWING ALERT DIALOG FOR CHOOSING OPTION BY CLICKING ON ITEM ADD

                R.id.nav_add -> {

                    if (isUserRegistered()){

                    var selectedItem = 0
                    val option = arrayOf("Add Your Car","Add Your Accomodation","Add Your Food")
                    val singleChoiceDialog=AlertDialog.Builder(this).setSingleChoiceItems(option,0){
                        dialogInterface,i->
                     selectedItem=i
                    }
                        .setPositiveButton("Accept"){_,_->

                            when (selectedItem) {
                                0 -> {
                                    // User selected "Add Car"
                                    val i  = Intent(this, AddingCar::class.java)
                                    startActivity(i)
                                }
                                1 -> {
                                    // User selected "Add Accommodation"
                                    val j = Intent(this, AddingAccomodation::class.java)
                                    startActivity(j)
                                }
                                2 -> {
                                    // User selected "Add Food"
                                    val intent = Intent(this,AddingFood::class.java)
                                    startActivity(intent)
                                }


                            }}
                        .setNegativeButton("Decline"){_,_-> }.create()
                  singleChoiceDialog.show()


                } else{
                        Toast.makeText(this, "You Are Not Registred", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,SignUpScreen::class.java)
                        startActivity(intent)
                    }}

//***************************************************************************************************************************


                R.id.nav_login-> {


                    // Toast.makeText(this, " you clicked Login Button", Toast.LENGTH_SHORT).show()
                   val intent = Intent(this, LonginScreen::class.java)
                   startActivity(intent)


                }R.id.Logout -> {

                logoutUser()

                   val intent = Intent(this, LonginScreen::class.java)
                   startActivity(intent)

            }

                R.id.data-> {

                    var selectedItem = 0
                    val option = arrayOf("My Car Data","My Accomodation Data","My Food Data")
                    val singleChoiceDialog=AlertDialog.Builder(this).setSingleChoiceItems(option,0){
                            dialogInterface,i->
                        selectedItem=i
                    }
                        .setPositiveButton("Accept"){_,_->

                            when (selectedItem) {
                                0 -> {
                                    // User selected "Add Car"
                                    val l  = Intent(this,CarProvider::class.java)
                                    startActivity(l)
                                }
                                1 -> {
                                    // User selected "Add Accommodation"
                                    val p = Intent(this, AccomodationProvider::class.java)
                                    startActivity(p)
                                }
                                2 -> {
                                    // User selected "Add Food"
                                    val intent= Intent(this,FoodProvider::class.java)
                                    startActivity(intent)
                                }

                            }}
                        .setNegativeButton("Decline"){_,_-> }.create()
                    singleChoiceDialog.show()

                }
            }

            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

        val firstFragment = CarFragment()
        val secondFragment = AccomodationFragment()
        val thirdFragment = FoodFragment()
        setCurrentFragment(firstFragment)

        //*******************************************************************************************************
        //*******************************************************************************************************
        //*******************************************************************************************************
                    //SETTING THE EACH FRAGMENT ON MAIN ACTIVITY  THROUGH BUTTOM NAVIGATON VIEW

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.taxi -> setCurrentFragment(firstFragment)
                R.id.acomo->setCurrentFragment(secondFragment)
                R.id.food->setCurrentFragment(thirdFragment)
            }
            true
        }
    }
    //*******************************************************************************************************
    //*******************************************************************************************************
    //*******************************************************************************************************

                      //REPLACING FRAGMENT BY FRAME LAYOUUT
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment, fragment)
            commit()

        }

    fun isUserRegistered(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }

    // Method to log out the user

    private fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        // Clear the "isRegistered" flag from SharedPreferences
        val sharedPrefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putBoolean("isRegistered", false)
        editor.apply()
        finish()

        // Replace the current Activity with the login fragment
        val k = Intent(this, LonginScreen::class.java)
        startActivity(k)
    }

}

