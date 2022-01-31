package com.example.dailynovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dailynovel.databinding.ActivityMainBinding
import com.example.dailynovel.databinding.ActivityReadingPageBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ReadingPage : AppCompatActivity() {

    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityReadingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val writedate = intent.getStringExtra("date").toString()
        val work = database.getReference("Diary")

        work.child(writedate).get().addOnSuccessListener {
            if(it.exists()){
                binding.readPageTopic.text = it.child("topic").value.toString()
                binding.readPageKind.text = it.child("kind").value.toString()
                binding.readPageNarrator.text = it.child("narrator").value.toString()
                binding.readPageWork.text = it.child("work").value.toString()
            }
        }

        binding.intentToUpdatepage.setOnClickListener(View.OnClickListener {
            val updateIntent = Intent(this, UpdatePage::class.java)
            updateIntent.putExtra("date", writedate)
            startActivity(updateIntent)
        })

        /*binding.yebi.setOnClickListener {
            val nextIntent = Intent(this, FirstPage::class.java)
            startActivity(nextIntent)
        }*/
    }
}