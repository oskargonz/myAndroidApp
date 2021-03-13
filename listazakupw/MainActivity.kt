package com.example.listazakupw

import android.app.Activity
import android.content.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.widget.Toast
import com.example.listazakupw.databinding.ActivityMainBinding
import com.example.listazakupw.databinding.ProductListActivityBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    var color_bt = Color.BLUE
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getSharedPreferences("pref_file_1", Context.MODE_PRIVATE)
        val intent = intent
        binding.bt1.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
        binding.bt2.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
        binding.bt3.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
        binding.tvEmail.text = intent.getStringExtra("user")

        binding.bt3.setOnClickListener {
            val str = et1.text.toString()
            binding.tv1.text = et1.text.toString()
            Toast.makeText(this, str, Toast.LENGTH_LONG).show()
        }

        binding.bt1.setOnClickListener {
            val intent_1 = Intent(this, ProductListActivity::class.java)
            intent_1.putExtra("et1_text", et1.text.toString())
            startActivity(intent_1)
        }

        binding.bt2.setOnClickListener {
            val intent_2 = Intent(this, OptionsActivity::class.java)
            startActivity(intent_2)
        }

        binding.btTomaps.setOnClickListener {
            val intent_3 = Intent(this, MapsActivity::class.java)
            startActivity(intent_3)
        }


    }
    override fun onStart() {
        super.onStart()


        //bindService(intent, mcom, Context, Context.BIND_AUTO_CREATE)
        //binding.tv1.text = sp.getString("tv1_text", "Default")


        //val intent = intent
        //val color_red = intent.getIntExtra("bt_color_red", Color.BLUE)
        //binding.bt1.setBackgroundColor(sp.getInt("bt_color", Color.BLUE))

    }

    override fun onStop() {
        super.onStop()
        /*
        if(mBound){
            unbindService(mcom)
            mBound = false
        }

         */

        //val editor = sp.edit()
        //editor.putString("tv1_text", binding.tv1.text.toString())
        //editor.putInt("color", color_bt)
        //editor.apply()
    }





}