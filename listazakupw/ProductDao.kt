package com.example.listazakupw

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product") // trzeba napisac swoje query, nie ma zadnej nazwy tabeli (W entity) dlatego bedzie student
    fun deleteAll()

}