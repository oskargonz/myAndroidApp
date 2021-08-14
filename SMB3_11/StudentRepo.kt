package com.example.myapp11

import androidx.lifecycle.LiveData

class StudentRepo(private val studentDao: StudentDao) {

    val allStudent: LiveData<List<Student>> = studentDao.getStudents()

    fun insert(student: Student) = studentDao.insert(student)

    fun update(student: Student) = studentDao.update(student)

    fun delete(student: Student) = studentDao.delete(student)

    fun deleteAll() = studentDao.deleteAll()
}