package com.example.soutenance

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soutenance.Util.FirestoreUtil
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.user_view.*

class ChatActivity:AppCompatActivity() {
    private lateinit var peopleSection: Section

    private lateinit var userListenerRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userListenerRegistration =
            FirestoreUtil.addUsersListener(this,this::updateRecyclerView)


        setContentView(R.layout.user_view)
    }
    private fun updateRecyclerView(items: List<Item>) {

        fun init() {
            recycler_view_people.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity.applicationContext)
                adapter = GroupAdapter<ViewHolder>().apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = peopleSection.update(items)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

    } private val onItemClick = OnItemClickListener { item, view ->
        if (item is PersonItem) {
            var intent=Intent(this, Chat::class.java)
            intent.putExtra(AppConstants.USER_NAME,item.person.nom)
            intent.putExtra(AppConstants.USER_ID,item.userId)
            startActivity(intent)
 }
    }

}

