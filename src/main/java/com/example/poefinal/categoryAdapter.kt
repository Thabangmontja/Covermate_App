package com.example.poefinal

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class categoryAdapter(private val cateList: ArrayList<categoryDbm>): RecyclerView.Adapter<categoryAdapter.categoryHolder>() {
    class categoryHolder(catView: View): RecyclerView.ViewHolder(catView){
        val categoryName:TextView = catView.findViewById(R.id.displayCategory)
        val catImage:ImageView = catView.findViewById(R.id.imageDisplay)
        val itemName: TextView = catView.findViewById(R.id.displayItem)
        val discription: TextView = catView.findViewById(R.id.displayDescriptions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_display,
        parent, false)
        return categoryHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cateList.size
    }

    override fun onBindViewHolder(holder: categoryHolder, position: Int) {
        val currentitem = cateList[position]
        holder.categoryName.setText(currentitem.categoryName.toString())
        holder.itemName.setText(currentitem.itemName.toString())
        holder.discription.setText(currentitem.description.toString())
        val bytes = android.util.Base64.decode(currentitem.image,
            android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.catImage.setImageBitmap(bitmap)
    }
}