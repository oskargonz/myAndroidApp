package com.example.listazakupw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_places_list.*

class PlacesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_list)
        val user = FirebaseAuth.getInstance().currentUser

        rv_places_list.layoutManager = LinearLayoutManager(this)
        rv_places_list.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val database = FirebaseDatabase.getInstance()
        val myRef3 = database.getReference().child("places").child(user!!.uid)
        val list = arrayListOf<Place>()

        rv_places_list.adapter = PlacesAdapter(this, list, myRef3)


    }
}