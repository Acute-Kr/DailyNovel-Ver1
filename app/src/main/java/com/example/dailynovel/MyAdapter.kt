package com.example.dailynovel

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynovel.databinding.ItemViewBinding

class MyAdapter(val contentList: ArrayList<ContentDate>) : RecyclerView.Adapter<MyAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.CustomViewHolder {
        val view = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false) // context:액티비티가 담고 있는 내용
        return CustomViewHolder(view).apply{
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val date = contentList[curPos].date
                val nextIntent = Intent(itemView.context, ReadingPage::class.java)
                nextIntent.putExtra("date",date.toString())
                ContextCompat.startActivity(itemView.context, nextIntent, null)
            }

        }
    }

    override fun onBindViewHolder(holder: MyAdapter.CustomViewHolder, position: Int) { //지속적으로 호출되면서 뷰와 데이터를 일치시켜줌
        val workYear = contentList[position].date.toString().substring(0,4)
        val workMonth = contentList[position].date.toString().substring(4,6)
        val workDay = contentList[position].date.toString().substring(6,8)

        holder.binding.item.text = "$workYear"+"년 "+"$workMonth"+"월 "+"$workDay"+"일의 이야기"
        //holder.bind(contentList.get(position))

    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    class CustomViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(layoutData: ContentDate) {
            binding.item.text = layoutData.date.toString()
        }
    }

}

