package com.example.soutenance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.nav_header.*
import org.w3c.dom.Text

class  ExpertActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var store:FirebaseFirestore
    lateinit var toolbar: Toolbar
    lateinit var ListView: ListView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expert)
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()
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
        val ruleRef: Task<QuerySnapshot> =store.collection("rules").whereEqualTo("IsApprouved","1").get()

        //Toast.makeText(this, ""+docRef.get(), Toast.LENGTH_SHORT).show()
        ruleRef.addOnSuccessListener {DocumentSnapshot->
            Log.d("tag", "succes")
            val x= DocumentSnapshot.toString()
            Toast.makeText(this,x,Toast.LENGTH_SHORT).show()
        }
        //ListView=findViewById(R.id.list_view)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout=findViewById(R.id.drawer_layout)
        navView=findViewById(R.id.nav_view)
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

                startActivity(intent)

            }
            R.id.nav_signout->{
                FirebaseAuth.getInstance().signOut()
                var intent=Intent(this, MainActivity::class.java)
                intent.putExtra("finish",true)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
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
            R.id.add -> {
                val intent=Intent(this, AjouterRegle::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.notif -> Toast.makeText(applicationContext,"notif clicked", Toast.LENGTH_SHORT).show()

        }
        return false
    }
}