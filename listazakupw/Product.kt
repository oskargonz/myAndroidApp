package com.example.listazakupw

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(var name: String, var quantity: String, var czy_kupione: Boolean){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}