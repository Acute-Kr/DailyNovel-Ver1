package com.example.dailynovel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.dailynovel.databinding.ActivityMain2Binding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SelectionPage : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    val database = Firebase.database

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        var stringIdArray : IntArray = intArrayOf(R.string.write_guide_1,R.string.write_guide_2,R.string.write_guide_3)
        var index = 0
        binding.writeGuideText.text = resources.getString(stringIdArray.get(index))

        val date: LocalDate = LocalDate.now() //날짜변환
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val dateFormat = date.format(formatter)

        binding.nextLevel.setOnClickListener(View.OnClickListener { //확인 버튼 누를때 마다 DB에 저장
            index++

            val work = database.getReference("Diary")
            if(index < stringIdArray.size){
                binding.writeGuideText.text = resources.getString(stringIdArray.get(index)) // index가 사이즈보다 적을때
            }

            if(index == 1){
                val topic = work.child(dateFormat).child("topic")
                topic.setValue(binding.writeGuideEdit.text.toString())
                binding.writeGuideEdit.text.clear()
            }

            else if (index == 2){
                // 전에 작성한 정보 저장
                val narrator = work.child(dateFormat).child("narrator")
                narrator.setValue(binding.writeGuideEdit.text.toString())

                // 버튼 눌렀을 경우 넘어가는 페이지
                binding.nextLevel.text = "작성페이지로"
                binding.nextLevel.setOnClickListener(View.OnClickListener {
                    val nextIntent = Intent(this, WritePage::class.java)
                    nextIntent.putExtra("date",dateFormat)
                    startActivity(nextIntent)
                })
                // 글의 종류 선택
                val kind = work.child(dateFormat).child("kind")
                binding.writeGuideEdit.setVisibility(View.GONE)
                binding.writeGuideRadio.setVisibility(View.VISIBLE)
                binding.writeGuideRadio.setOnCheckedChangeListener{group, checkedId ->
                    when(checkedId) {
                        R.id.check_poem -> kind.setValue("시")
                        R.id.check_freeWork -> kind.setValue("자유")
                    }
                }
            }

            else if (index == 3){
                val kind = work.child(dateFormat).child("kind")
                kind.setValue(binding.writeGuideEdit.text.toString())
            }

        })



    }
}