package com.example.listazakupw

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase

class ProductViewModel (app: Application) : AndroidViewModel(app){
    private val repo: ProductRepo //odwolanie do repository
    val allProducts: LiveData<List<Product>>

    init {

        repo = ProductRepo(ProductDB.getDatabase(app).productDao()) //na tym repo mozna inicjalizowac studentow
        allProducts = repo.allProduct
    }

    fun insert(product: Product) = repo.insert(product)

    fun update(product: Product) = repo.update(product)

    fun delete(product: Product) = repo.delete(product)

    fun deleteAll() = repo.deleteAll()


}