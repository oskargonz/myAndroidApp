package com.example.myapp11

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {

    @Query("SELECT * FROM student")
    fun getStudents(): LiveData<List<Student>>

    @Insert
    fun insert(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("DELETE FROM student")
    fun deleteAll()
}