package com.example.dailynovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import com.example.dailynovel.databinding.ActivityUpdatePageBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UpdatePage : AppCompatActivity() {

    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityUpdatePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val work = database.getReference("Diary")
        val intent = getIntent()
        val writedate = intent.getStringExtra("date").toString()
        val kind = work.child(writedate).child("kind")

        work.child(writedate).get().addOnSuccessListener {
            if(it.exists()){
                binding.updatePageTopic.text = Editable.Factory.getInstance().newEditable(it.child("topic").value.toString())
                if(it.child("kind").value.toString() == "시"){
                    binding.updatePoemRadio.isChecked = true
                }
                else if(it.child("kind").value.toString() == "자유"){
                    binding.updateFreeRadio.isChecked = true
                }
                binding.updatePageNarrator.text = Editable.Factory.getInstance().newEditable(it.child("narrator").value.toString())
                binding.updatePageWork.text = Editable.Factory.getInstance().newEditable(it.child("work").value.toString())
            }
        }

        binding.updateFinishBtn.setOnClickListener(View.OnClickListener {

            work.child(writedate).child("topic").setValue(binding.updatePageTopic.text.toString())
            //work.child(writedate).child("kind").setValue(binding.updatePageKind.text.toString())
            work.child(writedate).child("narrator").setValue(binding.updatePageNarrator.text.toString())
            work.child(writedate).child("work").setValue(binding.updatePageWork.text.toString())
            if(binding.updatePoemRadio.isChecked == true){
                kind.setValue("시")
            }
            else if(binding.updateFreeRadio.isChecked == true){
                kind.setValue("자유")
            }

            val nextIntent = Intent(this, FirstPage::class.java)
            startActivity(nextIntent)

        })



    }
}