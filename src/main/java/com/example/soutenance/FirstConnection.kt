package com.example.soutenance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*

class FirstConnection  : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()
        if (auth.currentUser != null) {
            val docRef: DocumentReference =store.collection("users").document(auth.currentUser.uid)
            Log.d("tag 1", docRef.get().toString())
            //Toast.makeText(this, ""+docRef.get(), Toast.LENGTH_SHORT).show()
            docRef.get()
            docRef.get().addOnSuccessListener {DocumentSnapshot->
                Log.d("tag", "succes")
                if (DocumentSnapshot.getString("isadmin")=="1") {
                    var intent=Intent(this, AdminActivity::class.java)
                    intent.putExtra("finish",true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    var intent=Intent(this, ExpertActivity::class.java)
                    intent.putExtra("finish",true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }

            }

        } else {
            var intent= Intent(this, MainActivity::class.java)
            intent.putExtra("finish",true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            }
        }
}
