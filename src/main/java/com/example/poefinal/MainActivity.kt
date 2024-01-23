package com.example.poefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poefinal.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: DatabaseReference

    private lateinit var categoryArrayList: ArrayList<categoryDbm>

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.addCategory.setOnClickListener {
            val intent = Intent(this, AddCategory::class.java)
            startActivity(intent)
        }

        binding.addItems.setOnClickListener {
            startActivity(Intent(this, GraphDisplay::class.java))
        }

        binding.viewItem.layoutManager = LinearLayoutManager(this)
        binding.viewItem.hasFixedSize()
        categoryArrayList = arrayListOf<categoryDbm>()
        getCategoryData()
    }

    private fun getCategoryData() {
        database = FirebaseDatabase.getInstance().getReference("Books")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (catsnapshot in snapshot.children){
                        val category = catsnapshot.getValue(categoryDbm::class.java)
                        categoryArrayList.add(category!!)
                    }
                    binding.viewItem.adapter = categoryAdapter(categoryArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
     fun viewAchievements(view: View){
         startActivity(Intent(this, Achievement::class.java))
    }

}