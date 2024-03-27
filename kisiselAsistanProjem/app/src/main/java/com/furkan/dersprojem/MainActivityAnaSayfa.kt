 package com.furkan.dersprojem

 import android.content.Intent
 import android.os.Bundle
 import android.widget.Toast
 import androidx.appcompat.app.AppCompatActivity
 import com.google.firebase.auth.FirebaseAuth
 import com.google.firebase.firestore.FirebaseFirestore
 import com.furkan.dersprojem.databinding.ActivityMainAnaSayfaBinding
 import android.widget.Button
 import android.widget.TextView
 import androidx.recyclerview.widget.LinearLayoutManager

 class MainActivityAnaSayfa : AppCompatActivity() {
     data class Goal(
         val title: String = "",
         val description: String = ""
     )

     private lateinit var auth: FirebaseAuth
     private lateinit var firestore: FirebaseFirestore
     private lateinit var binding: ActivityMainAnaSayfaBinding
     private lateinit var userId: String
     private val goalList = mutableListOf<Goal>()

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityMainAnaSayfaBinding.inflate(layoutInflater)
         setContentView(binding.root)
         val recyclerView = binding.recyclerViewGoals
         val adapter = GoalsAdapter(goalList)
         recyclerView.adapter = adapter
         recyclerView.layoutManager = LinearLayoutManager(this)

         val btnListeyiGoster: Button = findViewById(R.id.btnListeyiGoster)

         // Diğer gerekli işlemler...

         btnListeyiGoster.setOnClickListener {
             showGoalsFromFirestore()
         }

         auth = FirebaseAuth.getInstance()
         firestore = FirebaseFirestore.getInstance()

         val email = intent.getStringExtra("email")
         val displayname = intent.getStringExtra("name")
         findViewById<TextView>(R.id.kullaniciMail).text = email + "\n" + displayname

         val currentUser = auth.currentUser
         currentUser?.let {
             userId = it.uid

             binding.kullaniciMail.text = "User: ${currentUser.displayName}"

             // Kullanıcının hedeflerini Firestore'dan alma işlemi
             showGoalsFromFirestore()
         }

         binding.btnAddGoal.setOnClickListener {
             val hedefText = binding.editTextGoal.text.toString().trim()

             if (hedefText.isNotEmpty()) {
                 val goalMap = hashMapOf(
                     "title" to hedefText, // Veriyi "title" olarak kaydetmek için
                     "description" to ""   // Varsayılan olarak boş bir "description" ekledik
                 )

                 firestore.collection("users").document(userId)
                     .collection("hedefler")
                     .add(goalMap)
                     .addOnSuccessListener {
                         Toast.makeText(this, "Hedef başarıyla eklendi.", Toast.LENGTH_SHORT).show()
                     }
                     .addOnFailureListener { exception ->
                         Toast.makeText(this, "Hedef eklerken bir hata oluştu: ${exception.message}", Toast.LENGTH_SHORT).show()
                     }
                 binding.editTextGoal.text.clear()
             } else {
                 Toast.makeText(this, "Lütfen bir hedef girin.", Toast.LENGTH_SHORT).show()
             }
         }

         binding.btnCikisYap.setOnClickListener {
             auth.signOut()
             val intent = Intent(applicationContext, MainActivity::class.java)
             startActivity(intent)
             finish()
         }
     }

     private fun displayGoals(goals: List<Goal>) {

         // Hedefleri ekranda gösterme işlemleri
     }

     private fun showGoalsFromFirestore() {
         goalList.clear()
         firestore.collection("users").document(userId)
             .collection("hedefler")
             .get()
             .addOnSuccessListener { documents ->
                 for (document in documents) {
                     val title = document.getString("title") ?: ""
                     val description = document.getString("description") ?: ""
                     val goal = Goal(title, description)
                     goalList.add(goal)
                 }
                 displayGoals(goalList) // Hedefleri ekranda göster
             }
             .addOnFailureListener { exception ->
                 Toast.makeText(this, "Hedefleri alırken bir hata oluştu: ${exception.message}", Toast.LENGTH_SHORT).show()
             }

     }
 }
