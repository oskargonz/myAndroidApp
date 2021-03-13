package com.example.listazakupw

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class Place(var name: String, var description: String, var radius: String, var promocjaDnia: String, var latitude: Double, var longitude: Double){

}