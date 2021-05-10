package com.example.soutenance

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin_admin.*
class SignInAdminActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_admin)
        auth = FirebaseAuth.getInstance()


        btn2_sign_in.setOnClickListener {
            doLogin()
        }
    }

private fun doLogin() {
    if(email2_text.text.toString().isEmpty()){
        email2_text.error="please enter  your mail"
        email2_text.requestFocus()
        return
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email2_text.text.toString()).matches()){
        email2_text.error="enter a valid mail"
        email2_text.requestFocus()
        return
    }
    if (password2Text.text.toString().isEmpty()){
        password2Text.error="please enter a password"
        password2Text.requestFocus()
        return
    }
    if (password2Text.text.toString().length<8){
        password2Text.error="enter a password of 8 caracter"
        password2Text.requestFocus()
        return
    }
    auth.signInWithEmailAndPassword(email2_text.text.toString(),password2Text.text.toString()).addOnCompleteListener(this){
        task ->
        if (task.isSuccessful){
            val user=auth.currentUser


        }else{ Toast.makeText(baseContext, "Sign in failed, problem in mail or password", Toast.LENGTH_SHORT).show()
        }
    }
}






}
