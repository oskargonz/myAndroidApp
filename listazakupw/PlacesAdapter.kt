package com.example.listazakupw

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listazakupw.databinding.ActivityMapsBinding
import com.example.listazakupw.databinding.ListElementBinding
import com.example.listazakupw.databinding.PlaceListElementBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlacesAdapter (val context: Context, var list: ArrayList<Place>, val ref: DatabaseReference) : RecyclerView.Adapter<PlacesAdapter.MyViewHolder>() {

    private var placesList = emptyList<Place>()
    init {
        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previous: String?) {
                CoroutineScope(Dispatchers.IO).launch {

                    var place = Place(
                            snapshot.child("name").value as String,
                        snapshot.child("description").value as String,
                        snapshot.child("radius").value as String,
                        snapshot.child("promocjaDnia").value as String,
                            snapshot.child("latitude").value as Double,
                            snapshot.child("longitude").value as Double
                    )
                    list.add(place)
                    withContext(Dispatchers.Main){
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
                    var place = Place(
                        snapshot.child("name").value as String,
                        snapshot.child("description").value as String,
                        snapshot.child("radius").value as String,
                        snapshot.child("promocjaDnia").value as String,
                            snapshot.child("latitude").value as Double,
                            snapshot.child("longitude").value as Double

                    )

                    list.remove(place)
                    list.add(place)
                    withContext(Dispatchers.Main){
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch{
                    var place = Place(
                        snapshot.child("name").value as String,
                        snapshot.child("description").value as String,
                        snapshot.child("radius").value as String,
                        snapshot.child("promocjaDnia").value as String,
                            snapshot.child("latitude").value as Double,
                            snapshot.child("longitude").value as Double
                    )
                    list.remove(place)
                    withContext(Dispatchers.Main){
                        notifyDataSetChanged()
                    }

                }
            }


        }
        )
    }

    inner class MyViewHolder(val binding: PlaceListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaceListElementBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PlacesAdapter.MyViewHolder, position: Int) {
        val ref1 = ref.child("places").orderByChild("name").equalTo(list[position].name)

        holder.binding.tvPlaceName.text = list[position].name
        holder.binding.tvPlacesDescription.text = list[position].description
        holder.binding.tvPlacesRadius.text = list[position].radius
        holder.binding.etPlacesPromocjaDnia.setText(list[position].promocjaDnia)

        holder.binding.tvPlaceName.setOnClickListener {
            ref.orderByChild("name").equalTo(list[position].name)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        CoroutineScope(Dispatchers.IO).launch {
                            snapshot.children.iterator().next().ref.removeValue()
                        }
                    }
                    override fun onCancelled(error: DatabaseError){
                        Log.e("PlacesAdapter", "Failed to delete value.", error.toException())
                    }
                })
        }



/*        UPDATE TODO
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
        }*/
    }



    override fun getItemCount(): Int = list.size

    fun setProducts(places: ArrayList<Place>){
        list = places
        notifyDataSetChanged() //informuje recyclerview ze dane sie zmienily

    }




}