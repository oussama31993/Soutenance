package com.example.soutenance

import androidx.appcompat.app.AppCompatActivity
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
import androidx.lifecycle.Transformations.map
import com.example.soutenance.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_expertajout.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.ajouter_regle.*

class AjouterRegle :  AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore
    private lateinit var sorti: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajouter_regle)
        auth = FirebaseAuth.getInstance()
        store= FirebaseFirestore.getInstance()
        val signedup=auth.currentUser

        ajouterRegle.setOnClickListener{
            ajout()
        }
    }
    private fun ajout() {
        val signedup = auth.currentUser
        val uid = signedup.uid
        val docRef: DocumentReference = store.collection("users").document(uid)
        Log.d("tag 1", docRef.get().toString())


        docRef.get().addOnSuccessListener { DocumentSnapshot ->
            Log.d("tag", "succes")
            val v = DocumentSnapshot.getString("nom")
            val rules = hashMapOf("id" to auth.currentUser.toString(), "nom" to v.toString(), "condition" to editTextTextPersonName3.text.toString(),
                    "action" to editTextTextPersonName4.text.toString(), "IsApprouved" to "0")
            val addOnFailureListener = store.collection("rules").document(signedup.uid)
                    .set(rules).addOnSuccessListener(OnSuccessListener {

                        Log.d(Companion.TAG, "succes")
                    }
                    ).addOnFailureListener(OnFailureListener { e -> Log.w(Companion.TAG, "error") })

        }
    }
    companion object {
        private const val TAG="log tag"
    }
}
