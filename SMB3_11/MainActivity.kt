package com.example.myapp11

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.example.myapp11.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getPreferences(Context.MODE_PRIVATE)
        //val button = findViewById<Button>(R.id.et1) //findViewByID
        binding.bt1.setOnClickListener {
            val str = binding.et1.text.toString()
            binding.tv1.text = str
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
            Log.i(getString(R.string.app_name), str)
        }
        binding.bt2.setOnClickListener {
            val intent1 = Intent(this, SecondaryActivity::class.java)
            intent1.putExtra("tv1text", tv1.text.toString())
            startActivity(intent1)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.tv1.text = sp.getString("tv1_text", "Default")
    }

    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.putString("tv1_text", binding.tv1.text.toString())
        editor.apply()
    }




}