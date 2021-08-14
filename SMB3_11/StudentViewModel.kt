package com.example.myapp11

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class StudentViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: StudentRepo
    val allStudents: LiveData<List<Student>>

    init {
        repo = StudentRepo(StudentDB.getDatabase(app).studentDao())
        allStudents = repo.allStudent
    }

    fun insert(student: Student) = repo.insert(student)

    fun update(student: Student) = repo.update(student)

    fun delete(student: Student) = repo.delete(student)

    fun deleteAll() = repo.deleteAll()
}