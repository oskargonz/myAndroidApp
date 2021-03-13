package com.example.listazakupw

import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listazakupw.databinding.ActivityMainBinding
import com.example.listazakupw.databinding.ProductListActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.product_list_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity(){
    //private lateinit var sp: SharedPreferences
    private lateinit var binding: ProductListActivityBinding



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ProductListActivityBinding.inflate(layoutInflater) // tu moze byc blad
        setContentView(binding.root)
        val sp = getSharedPreferences("pref_file_1", Context.MODE_PRIVATE)
        binding.bt4.setBackgroundColor(sp.getInt("button_color", Color.BLUE))

        val intent = intent
        intent.getStringExtra("et1_text")
        binding.tv3.text = intent.getStringExtra("et1_text")


        //Zapis do bazy danych
        val database = FirebaseDatabase.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val myRef2 = database.getReference().child("users").child(user!!.uid)
        //val product_ref = myRef2.child("products")
        val list = arrayListOf<Product>()

        //Adapter
        binding.rv1.layoutManager = LinearLayoutManager(this)
        binding.rv1.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        binding.rv1.adapter = MyAdapter(this, list, myRef2)

        //Zapis na klik na buttonie
        binding.bt4.setOnClickListener {
            val product = Product(binding.etName.text.toString(),
                    binding.etQuantity.text.toString(),
                    binding.cb1.isChecked)
            CoroutineScope(Dispatchers.IO).launch{
                myRef2.push().setValue(product)
            }
            Toast.makeText(this, "ADD ITEM", Toast.LENGTH_SHORT).show()
        }

        //Remove all products
        binding.bt4.setOnLongClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                myRef2.removeValue()
            }
            Toast.makeText(this, "ADD ITEM", Toast.LENGTH_SHORT).show()
            true
        }

        /*//Broadcast receiver
        val explicitBroadcastRemote = Intent()
        explicitBroadcastRemote.component = ComponentName("com.example.mini_project_2",  "com.example.mini_project_2.MyReceiver_2")
        sendBroadcast(explicitBroadcastRemote, "com.example.listazakupw.MY_PERMISSION")*/

        












       /* //val lista = arrayListOf<String>("Element_1", "Element_2", "Element_3")
        val productViewModel = ProductViewModel(application) //tu trzeba przekazac aplikacje (w nawiasach)
        val adapter = MyAdapter(productViewModel)
        binding.rv1.adapter = adapter
        productViewModel.allProducts.observe(this, Observer { products ->
            products.let {
                adapter.setProducts(it)
            }
        })

        binding.bt4.setOnClickListener {
            //dodaje produkty
            productViewModel.insert(Product(
                binding.etName.text.toString(),
                binding.etQuantity.text.toString(),
                binding.cb1.isChecked)
            )*/

            // UTWORZENIE INTENTU I ROZGLOSZENIE:
            //val broadcastIntent = Intent("com.example.helloworld.DODANO_PRODUKT") //com.example.helloworld.DODANO_PRODUKT to jest moj intent filter


            /*
            explicitBroadcastRemote.setClassName(
                    "com.example.mini_project_2",
                    "com.example.mini_project_2.MyReceiver_2"
            )
*/

            //explicitBroadcastRemote.putExtra("nazwa", binding.etName.text.toString())
            //broadcast receiver jest zarejestrowany statycznie w android manifest dlatego tutaj musze sie do niego odniesc
            //moglbym zrobic dynamicznie ale w mini projekcie bede wykorzystywal statyczna - druga aplikacja nie bedzie miala w ogole gui interface wiec musze dodac component
            //tu jest latwo ale w moim projekcie musze odniesc sie do package name, a pozniej odwolanie package name . receiver - broadcastIntent.component = ComponentName("", "")
            //explicitBroadcastRemote.component = ComponentName(this, MyReceiver::class.java)
            //sendBroadcast(explicitBroadcastRemote) // metoda rozglaszajaca
            //sendOrderedBroadcast(broadcastIntent, Manifest.permission.MY-PERMISSION) - rozgloszenie z permission, w manifest musi byc zdefiniowana nazwa permission - 24:00 wyklad - opisane jest jak to zrobic
            //do powyzszego - trzeba zdefiniowac w manifest nad application <permission android=name="com.example.myapp/helloworld.MY_PERMISSION"/>
            // /UTWORZENIE INTENTU I ROZGLOSZENIE:



        }

/*        binding.bt4.setOnLongClickListener {
            productViewModel.deleteAll()
            true
        }*/




    /*
    override fun onStart() {
        super.onStart()
        binding.tv3.text = sp.getString("tv3_text", "-")
    }

    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.putString("tv3_text", binding.tv3.toString())
        editor.apply()
    }

     */

}