package com.example.soutenance

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Transformations.map
import com.example.soutenance.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_expertajout.*
import kotlinx.android.synthetic.main.activity_signup.*

class ExpertAjout:  AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore
    private lateinit var sorti: String
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expertajout)
        auth = FirebaseAuth.getInstance()
        store= FirebaseFirestore.getInstance()
        val signedup=auth.currentUser

        btn3_sign_up.setOnClickListener {
            signUser()
        }
        val Spinner=findViewById<Spinner>(R.id.spinner)
        val spin=resources.getStringArray(R.array.spin)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spin)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val text:String=parent?.getItemAtPosition(position).toString()
                sorti=text
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun signUser() {
        if(email3_text.text.toString().isEmpty()){
            email3_text.error="please enter  your mail"
            email3_text.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email3_text.text.toString()).matches()){
            email3_text.error="enter a valid mail"
            email3_text.requestFocus()
            return
        }
        if (password3Text.text.toString().isEmpty()){
            password3Text.error="please enter a password"
            password3Text.requestFocus()
            return
        }
        if (password3Text.text.toString().length<8){
            password3Text.error="enter a password of 8 caracter"
            password3Text.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(email3_text.text.toString(),password3Text.text.toString()).addOnCompleteListener(this){
            task ->
            if (task.isSuccessful){
                val signedup=auth.currentUser
                val user= hashMapOf("id" to auth.currentUser.toString(),"mail" to email3_text.text.toString()
                ,"isadmin" to "0", "nom" to name_text.text.toString(),"speciality" to sorti)
                store.collection("users").document(signedup.uid)
                        .set(user).addOnSuccessListener(OnSuccessListener {

                            Log.d(Companion.TAG,"succes")
                        }
                        ).addOnFailureListener(OnFailureListener { e-> Log.w(Companion.TAG,"error") }

                        )
                startActivity(Intent(this,AdminActivity::class.java))



                Toast.makeText(baseContext,"signed up successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(baseContext,"sign up failed, try again after some time", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG="log tag"
    }
}