package com.example.opsc2

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.opsc2.databinding.ActivityMainBinding
import com.example.opsc2.databinding.ActivityOpsc2Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityOpsc2Binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

binding= ActivityOpsc2Binding.inflate(layoutInflater)
        setContentView(binding.root)
firebaseAuth=FirebaseAuth.getInstance()
        binding.button5.setOnClickListener {
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
val email = binding.editTextTextEmailAddress.text.toString()
            val password =binding.editTextTextPassword2.text.toString()


            if(email.isNotEmpty() && password.isNotEmpty()) {
               firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                   if (it.isSuccessful){
                       val intent = Intent(this,SigninActivity::class.java)
                  startActivity(intent)
                   }else
                   {
                       Toast.makeText(this,"empty", Toast.LENGTH_SHORT).show()
                   }
            }

        }
    //    setSupportActionBar(binding.toolbar)

   //     val navController = findNavController(R.id.nav_host_fragment_content_main)
    //    appBarConfiguration = AppBarConfiguration(navController.graph)
  //      setupActionBarWithNavController(navController, appBarConfiguration)

   //     binding.fab.setOnClickListener { view ->
  //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
 //               .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

  //  override fun onSupportNavigateUp(): Boolean {
   //     val navController = findNavController(R.id.nav_host_fragment_content_main)
    //    return navController.navigateUp(appBarConfiguration)
  //              || super.onSupportNavigateUp()
 //   }
}