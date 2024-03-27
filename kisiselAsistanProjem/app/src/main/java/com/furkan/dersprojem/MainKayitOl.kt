package com.furkan.dersprojem

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.furkan.dersprojem.databinding.ActivityMainKayitOlBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.example.yourpackage.databinding.ActivityMainKayitOlBinding

class MainKayitOl : AppCompatActivity() {

    private lateinit var binding: ActivityMainKayitOlBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKayitOlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnKaydet.setOnClickListener {
            val email = binding.kayitKullanici.text.toString()
            val password = binding.kayitParola.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@MainKayitOl,
                            "Registration Successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MainActivityAnaSayfa::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@MainKayitOl,
                            "Registration Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        binding.btnGiriseDon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
