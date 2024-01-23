package com.example.poefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.poefinal.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.redirectReg.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        binding.registerBtn.setOnClickListener {
            val emailTxt = binding.regEmail.text.toString()
            val passwordTxt = binding.regPassword.text.toString()
            val conPassword = binding.cornPass.text.toString()

            if(emailTxt.isNotEmpty()&& passwordTxt.isNotEmpty()&& conPassword.isNotEmpty()){
                if(passwordTxt == conPassword){
                    auth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(this){
                        if(it.isSuccessful){
                            Toast.makeText(this, "Registered successfully!!!!", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, LoginPage::class.java)
                            startActivity(intent)
                            binding.regEmail.text.clear()
                            binding.regPassword.text.clear()
                            binding.cornPass.text.clear()
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Password not match!!!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }else{
                Toast.makeText(this, "Field cannot be empty!!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}