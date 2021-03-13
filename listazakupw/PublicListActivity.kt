package com.example.listazakupw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listazakupw.databinding.ProductListActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_public_list.*
import kotlinx.android.synthetic.main.product_list_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PublicListActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_public_list)

        rv2.layoutManager = LinearLayoutManager(this)
        rv2.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val database = FirebaseDatabase.getInstance()

        val myRef = database.getReference().child("users_public")

        val list_public = arrayListOf<Product>()
        rv2.adapter = MyAdapter(this, list_public, myRef)

        btDodajPublic.setOnClickListener {
            val product = Product(
                etNamePublic.text.toString(),
                etQuantityPublic.text.toString(),
                cbPublic.isChecked)

            CoroutineScope(Dispatchers.IO).launch{
                myRef.push().setValue(product)

            }


            Toast.makeText(this, "ADD ITEM", Toast.LENGTH_SHORT).show()
        }

        btDodajPublic.setOnLongClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                myRef.removeValue()
            }
            Toast.makeText(this, "ADD ITEM", Toast.LENGTH_SHORT).show()
            true
        }

    }
}