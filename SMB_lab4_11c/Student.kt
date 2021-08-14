package com.example.myapp11

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(var name: String, var surname: String, var absolwent: Boolean){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}