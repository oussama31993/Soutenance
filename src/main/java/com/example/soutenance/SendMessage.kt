package com.example.soutenance

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_expertajout.*
import kotlinx.android.synthetic.main.nav_header.*

class SendMessage: AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_message)
        val usr = auth.currentUser
        val uid = usr.uid

        setupUser()
    }


    private fun setupUser() {
        val usr = auth.currentUser
        val name = text_user.toString()
        val uid = usr.uid


    }
}
