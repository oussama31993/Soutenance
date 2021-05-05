package com.example.soutenance

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        btn2_signup.setOnClickListener {
            signUser()
        }
    }

    private fun signUser() {
        if(mail_text.text.toString().isEmpty()){
            mail_text.error="please enter  your mail"
            mail_text.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail_text.text.toString()).matches()){
            mail_text.error="enter a valid mail"
            mail_text.requestFocus()
            return
        }
        if (pass_text.text.toString().isEmpty()){
            pass_text.error="please enter a password"
            pass_text.requestFocus()
            return
        }
        if (pass_text.text.toString().length<8){
            pass_text.error="enter a password of 8 caracter"
            pass_text.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(mail_text.text.toString(),pass_text.text.toString()).addOnCompleteListener(this){
                task ->
            if (task.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else {
                Toast.makeText(baseContext,"sign up failed, try again after some time", Toast.LENGTH_SHORT).show()
            }
        }
    }
}