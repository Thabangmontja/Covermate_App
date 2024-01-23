package com.example.poefinal

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.poefinal.databinding.ActivityAddCategoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class AddCategory : AppCompatActivity() {
    private lateinit var binding: ActivityAddCategoryBinding
    private lateinit var database: DatabaseReference
    private lateinit var progressDialog: ProgressDialog
    var sImage: String? =""
    private var our_request_code = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
    fun insert_data(view: View) {
        val categoryName = binding.categoryText.text.toString()
        val itemName = binding.itemName.text.toString()
        val desc = binding.description.text.toString()

        if (categoryName.isNotEmpty() && itemName.isNotEmpty() && desc.isNotEmpty()) {
            database = FirebaseDatabase.getInstance().getReference("Books")
            val catagories = categoryDbm(categoryName, sImage.toString(), itemName, desc)
            val databaseReference = FirebaseDatabase.getInstance().reference
            val id = databaseReference.push().key
            database.child(id.toString()).setValue(catagories).addOnCompleteListener {
                binding.categoryText.text.clear()
                sImage = ""
                Toast.makeText(
                    this,
                    "Category has been created successfully!!!!!!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this, "Fail to create category", Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            Toast.makeText(this, "Field cannot be empty.......", Toast.LENGTH_SHORT)
                .show()
        }
    }
    fun insert_Image(view: View){
        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        ActivityResultLauncher.launch(myfileintent)

    }
    private val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){
        result: ActivityResult ->
        if (result.resultCode == RESULT_OK){
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val bytes = stream.toByteArray()
                sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                binding.imageView.setImageBitmap(myBitmap)
                inputStream!!.close()
                Toast.makeText(this, "Image selected successfully!!", Toast.LENGTH_SHORT)
                    .show()
            }catch (ex: Exception){
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
    fun TakeImage(view: View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager)!=null)
        {
            startActivityForResult(intent,our_request_code)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == our_request_code && resultCode == RESULT_OK)
        {
            val imageView : ImageView = findViewById(R.id.imageView);
            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
        }
    }

}