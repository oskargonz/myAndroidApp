package com.example.myapp11

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDB : RoomDatabase(){

    abstract fun studentDao(): StudentDao

    companion object{
        private var instance: StudentDB? = null

        fun getDatabase(context: Context): StudentDB{
            if( instance != null )
                return instance as StudentDB
            instance = Room.databaseBuilder(
                context.applicationContext,
                StudentDB::class.java,
                "studentDB"
            ).allowMainThreadQueries().build()
            return instance as StudentDB
        }
    }
}