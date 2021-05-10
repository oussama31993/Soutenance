package com.example.soutenance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.nav_header.*

class AdminActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout=findViewById(R.id.drawer_layout)
        navView=findViewById(R.id.nav_view)
        auth = FirebaseAuth.getInstance()
        store= FirebaseFirestore.getInstance()
        val user=auth.currentUser
        val uid=user.uid
        val docRef: DocumentReference =store.collection("users").document(uid)
        Log.d("tag 1", docRef.get().toString())
        //Toast.makeText(this, ""+docRef.get(), Toast.LENGTH_SHORT).show()
        docRef.get()
        docRef.get().addOnSuccessListener {DocumentSnapshot->
            Log.d("tag", "succes")
        text_user.text= DocumentSnapshot.getString("nom")
            }
        var toggle= ActionBarDrawerToggle(
            this,drawerLayout,toolbar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener (this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_profile->{
                Toast.makeText(this,"profil clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_message->{
                var intent=Intent(this, ChatActivity::class.java)
                intent.putExtra("name",text_user.text.toString())
                startActivity(intent)}
            R.id.nav_signout->{
                FirebaseAuth.getInstance().signOut()
                var intent=Intent(this, MainActivity::class.java)
                intent.putExtra("finish",true)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            R.id.nav_ajoutExpert->{
                val intent=Intent(this, ExpertAjout::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK).or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemView=item.itemId
        when(itemView){
            R.id.add -> Toast.makeText(applicationContext,"add clicked", Toast.LENGTH_SHORT).show()
            R.id.notif -> Toast.makeText(applicationContext,"notif clicked", Toast.LENGTH_SHORT).show()

        }
        return false
    }
}