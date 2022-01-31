package com.example.dailynovel

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.dailynovel.databinding.ActivityMain2Binding
import com.example.dailynovel.databinding.ActivityMain3Binding
import com.example.dailynovel.databinding.FreeGuideBinding
import com.example.dailynovel.databinding.GuideBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WritePage : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    val database = Firebase.database

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val guideBinding = GuideBinding.inflate(layoutInflater)

        val date = database.getReference("Diary")
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val writedate = intent.getStringExtra("date").toString()

        //val data = date.child(writedate).child("topic").get()
        //binding.writePageTopic.text = data.toString()
        var kind = ""



        date.child(writedate).get().addOnSuccessListener {
            if(it.exists()){
                binding.writePageTopic.text = it.child("topic").value.toString()
                binding.writePageNarrator.text = it.child("narrator").value.toString()
                binding.writePageKind.text = it.child("kind").value.toString()

                if(it.child("kind").value.toString() == "시"){
                    showPoemGuidePopup()
                }
                else if(it.child("kind").value.toString() == "자유"){
                    showFreeGuidePopup()
                }
            }
        }



        binding.finishBtn.setOnClickListener(View.OnClickListener {

              val work = date.child(writedate).child("work")
              work.setValue(binding.work.text.toString())
            val nextIntent = Intent(this, FirstPage::class.java)
            startActivity(nextIntent)
        })




    }

    private fun showPoemGuidePopup(){
        val guideBinding = GuideBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Guide")
            .setView(guideBinding.root)
            .create()

        alertDialog.show()
        alertDialog.window?.setLayout(1000,1000)
    }

    private fun showFreeGuidePopup(){
        val guideBinding = FreeGuideBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Guide")
            .setView(guideBinding.root)
            .create()
        alertDialog.show()
        alertDialog.window?.setLayout(1000,1000)
    }
}