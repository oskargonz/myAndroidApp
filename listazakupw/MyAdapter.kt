package com.example.listazakupw

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listazakupw.databinding.ActivityMainBinding
import com.example.listazakupw.databinding.ListElementBinding
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MyAdapter (val context: Context, var list: ArrayList<Product>, val ref: DatabaseReference) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//old - class MyAdapter (val productViewModel: ProductViewModel) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()

    private var productList = emptyList<Product>()
    init {
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previous: String?) {
                CoroutineScope(Dispatchers.IO).launch {
                    /*for(messageSnapshot in snapshot.children){
                        var product = Product(
                                messageSnapshot.child("name").value as String,
                                messageSnapshot.child("quantity").value as String,
                                messageSnapshot.child("czyKupione").value as Boolean
                        )*/
                    var product = Product(
                            snapshot.child("name").value as String,
                            snapshot.child("quantity").value as String,
                            snapshot.child("czy_kupione").value as Boolean
                        )
                        list.add(product)
                    withContext(Main){
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                CoroutineScope(Dispatchers.IO).launch {
                    var product = Product(
                            snapshot.child("name").value as String,
                            snapshot.child("quantity").value as String,
                            snapshot.child("czy_kupione").value as Boolean
                    )

                    list.remove(product)
                    list.add(product)
                    withContext(Main){
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch{
                    var product = Product(
                            snapshot.child("name").value as String,
                            snapshot.child("quantity").value as String,
                            snapshot.child("czy_kupione").value as Boolean
                    )
                    list.remove(product)
                    withContext(Main){
                        notifyDataSetChanged()
                    }

                }
            }


        }
        )
    }

    inner class MyViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ref1 = ref.child("users").orderByChild("name").equalTo(list[position].name)

        //holder.binding.tvId.text = position.toString()
        holder.binding.tvName.text = list[position].name
        holder.binding.tvQuantity.text = list[position].quantity
        holder.binding.cbKupione.isChecked = list[position].czy_kupione
/*        holder.binding.tvName.setOnClickListener{
            val name = holder.binding.tvName.text.toString()
            ref.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot){
                    val response = snapshot.value as HashMap<String, HashMap<String, Objects>>
                    val productID = response.keys.first()

                    ref.child(productID).removeValue()
                }

                override fun onCancelled(error: DatabaseError){
                    Log.e("MyAdapter", "Failed to delete value.", error.toException())
                }

            }
            )

        }*/
        holder.binding.tvName.setOnClickListener {
            //ref = ref.child("users").orderByChild("name").equalTo(list[position].name)

            //ref.orderByValue().equalTo(list[position].id.toString())
            //ref.child("users").orderByChild("name").equalTo(list[position].id.toString())

            ref.orderByChild("name").equalTo(list[position].name)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //snapshot.children.iterator().next().ref.removeValue()
                        CoroutineScope(Dispatchers.IO).launch {
                            snapshot.children.iterator().next().ref.removeValue()
                            //snapshot.ref.removeValue()
                        }
                    }
                    override fun onCancelled(error: DatabaseError){
                        Log.e("MyAdapter", "Failed to delete value.", error.toException())
                    }
                })
            }

/*        holder.binding.root.setOnClickListener {
            productViewModel.delete(productList[position]) //usuniecie studenta
            notifyDataSetChanged()
        }

        holder.binding.cbKupione.setOnClickListener {
            productList[position].czy_kupione = holder.binding.cbKupione.isChecked
            productViewModel.update(productList[position])
            notifyDataSetChanged()
        }*/
        holder.binding.cbKupione.setOnClickListener {
            val isChecked = holder.binding.cbKupione.isChecked
            list[position].czy_kupione = isChecked

            ref.orderByChild("name").equalTo(list[position].name)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            CoroutineScope(Dispatchers.IO).launch {
                                val map = HashMap<String, Any>()
                                map.put("czy_kupione", isChecked)
                                snapshot.children.iterator().next().ref.updateChildren(map)
                            }
                        }
                        override fun onCancelled(error: DatabaseError){
                            Log.e("MyAdapter", "Failed to update value.", error.toException())
                        }
                    })
        }

    }

    override fun getItemCount(): Int = list.size

    fun setProducts(products: ArrayList<Product>){
        list = products
        notifyDataSetChanged() //informuje recyclerview ze dane sie zmienily

    }


}