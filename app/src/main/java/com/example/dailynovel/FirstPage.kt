package com.example.dailynovel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynovel.databinding.ActivityMainBinding
import com.example.dailynovel.databinding.ItemViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FirstPage : AppCompatActivity() {
    val database = Firebase.database

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val date: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val dateFormat = date.format(formatter)
        val Diary = "Diary"
        val contentList = arrayListOf<ContentDate>()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val work = database.getReference(Diary)

        binding.intentToWritepageBtn.setOnClickListener {
            val nextIntent = Intent(this, SelectionPage::class.java)
            startActivity(nextIntent)

            work.child(dateFormat).setValue("")
        }

        work.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) { //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                contentList.clear()
                for(snapshot in dataSnapshot.children) {
                    val date = snapshot.getKey().toString()
                    var dateInt : Int = date.toInt()
                    contentList.add(ContentDate(dateInt))
                }
                binding.itemRecycler.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding.itemRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.itemRecycler.setHasFixedSize(true)
        binding.itemRecycler.adapter = MyAdapter(contentList)



    }
}

