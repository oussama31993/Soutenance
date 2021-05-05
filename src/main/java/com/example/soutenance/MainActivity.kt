package com.example.soutenance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.System.getString
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.grpc.internal.JsonUtil.getString
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.*
import org.w3c.dom.Document

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()

            setContentView(R.layout.activity_main)
            btn_sign_in.setOnClickListener {
                doLogin()

        }
    }
    private fun doLogin() {
        if(email_text.text.toString().isEmpty()){
            email_text.error="please enter  your mail"
            email_text.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_text.text.toString()).matches()){
            email_text.error="enter a valid mail"
            email_text.requestFocus()
            return
        }
        if (passwordText.text.toString().isEmpty()){
            passwordText.error="please enter a password"
            passwordText.requestFocus()
            return
        }
        if (passwordText.text.toString().length<8){
            passwordText.error="enter a password of 8 caracter"
            passwordText.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(email_text.text.toString(),passwordText.text.toString()).addOnCompleteListener(this){
            task ->
            if (task.isSuccessful){

                val user=auth.currentUser
                updateUI(user)

            }else{
                updateUI(null)
                Toast.makeText(baseContext, "Sign in failed, problem in mail or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUserAccessLevel(uid: String) {

        val docRef: DocumentReference =store.collection("users").document(uid)
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
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }



    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null){
          checkUserAccessLevel(currentUser.uid)
        }else
        {
            // Toast.makeText(baseContext, "Sign in failed, problem in mail or password", Toast.LENGTH_SHORT).show()
        }
    }


}