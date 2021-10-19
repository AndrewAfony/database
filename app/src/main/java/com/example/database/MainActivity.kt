package com.example.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.database.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//    private fun saveToFile(){
//        val text = binding.editText.text.toString()
//        this.openFileOutput(FILE_NAME, MODE_APPEND).use {
//            it.write(text.toByteArray())
//            it.write("\n".toByteArray())
//        }
//
//        binding.editText.text.clear()
//
//        binding.filePath.text = this.openFileInput(FILE_NAME).bufferedReader().useLines { lines ->
//            lines.fold(""){_, text ->
//                text
//            }
//        }
//    }

//    override fun onBackPressed() {
//        val navHost = this.supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
//        val current = navHost?.childFragmentManager?.fragments?.get(0)?.id
//
//        Toast.makeText(this, "Current id: $current \n titleF: ${R.id.titleFragment}", Toast.LENGTH_LONG).show()
//        if (navHost?.childFragmentManager?.fragments?.get(0)?.isVisible == true){
//            AlertDialog.Builder(this).apply {
//                setTitle("Confirmation")
//                setMessage("Are you sure you want to exit the app?")
//
//                setPositiveButton("Yes") { _, _ ->
//                    // if user press yes, then exit from app
//                    super.onBackPressed()
//                }
//
//                setNegativeButton("No") { _, _ ->
//                    // if user press no, then return the activity
//                    Toast.makeText(
//                        this@MainActivity, "Thank you",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }.show()
//        } else {
//            super.onBackPressed()
//        }
//    }
    }
}