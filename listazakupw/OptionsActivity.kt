package com.example.listazakupw

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.listazakupw.databinding.ActivityMainBinding
import com.example.listazakupw.databinding.OptionsActivityBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.options_activity.*

class OptionsActivity : AppCompatActivity(){
    private lateinit var sp: SharedPreferences
    private lateinit var binding: OptionsActivityBinding
    val color = Color.BLUE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OptionsActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val sp = getSharedPreferences("pref_file_1", Context.MODE_PRIVATE)
        val editor = sp.edit()
        binding.btCzerwone.setBackgroundColor(sp.getInt("button_color", Color.GREEN))
        binding.btZielone.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
        binding.btNiebieskie.setBackgroundColor(sp.getInt("button_color", Color.BLUE))

        binding.btNiebieskie.setOnClickListener {
            editor.putInt("button_color", Color.BLUE)
            editor.apply()
            binding.btCzerwone.setBackgroundColor(sp.getInt("button_color", Color.GREEN))
            binding.btZielone.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
            binding.btNiebieskie.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
        }

        binding.btZielone.setOnClickListener {
            editor.putInt("button_color", Color.GREEN)
            editor.apply()
            binding.btCzerwone.setBackgroundColor(sp.getInt("button_color", Color.GREEN))
            binding.btZielone.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
            binding.btNiebieskie.setBackgroundColor(sp.getInt("button_color", Color.BLUE))

        }

        binding.btCzerwone.setOnClickListener {
            editor.putInt("button_color", Color.RED)
            editor.apply()
            binding.btCzerwone.setBackgroundColor(sp.getInt("button_color", Color.GREEN))
            binding.btZielone.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
            binding.btNiebieskie.setBackgroundColor(sp.getInt("button_color", Color.BLUE))
        }



/*

        binding.btNiebieskie.setOnClickListener {
            binding.btNiebieskie.setBackgroundColor(Color.BLUE)
            binding.btCzcionki.setBackgroundColor(Color.BLUE)
            binding.btCzerwone.setBackgroundColor(Color.BLUE)
            binding.btTlo.setBackgroundColor(Color.BLUE)
            binding.btZielone.setBackgroundColor(Color.BLUE)
            val intent_3 = Intent(this, MainActivity::class.java)
            val color = Color.BLUE
            startActivity(intent_3)
            //intent_3.putExtra("bt_color_blue", Color.BLUE)

        }

        binding.btCzerwone.setOnClickListener {
            binding.btNiebieskie.setBackgroundColor(Color.RED)
            binding.btCzcionki.setBackgroundColor(Color.RED)
            binding.btCzerwone.setBackgroundColor(Color.RED)
            binding.btTlo.setBackgroundColor(Color.RED)
            binding.btZielone.setBackgroundColor(Color.RED)
            val intent_4 = Intent(this, MainActivity::class.java)
            intent_4.putExtra("bt_color_red", Color.RED)
            val color = Color.RED
            //startActivity(intent_4)
        }

        binding.btZielone.setOnClickListener {
            binding.btNiebieskie.setBackgroundColor(Color.GREEN)
            binding.btCzcionki.setBackgroundColor(Color.GREEN)
            binding.btCzerwone.setBackgroundColor(Color.GREEN)
            binding.btTlo.setBackgroundColor(Color.GREEN)
            binding.btZielone.setBackgroundColor(Color.GREEN)
            val intent_5 = Intent(this, MainActivity::class.java)
            intent_5.putExtra("bt_color_red", Color.GREEN)
            val color = Color.GREEN
            startActivity(intent_5)
        }
        */

    }
/*
    override fun onStart() {
        super.onStart()
        binding.btNiebieskie.setBackgroundColor(sp.getInt("bt_color", Color.BLUE))
    }
*/
    /*
    override fun onStop() {
        super.onStop()
        val editor = sp.edit()
        editor.putInt("bt_color", color)
        editor.apply()
        //val intent_5 = Intent(this, MainActivity::class.java)
        //intent_5.putExtra("bt_color_red", color)


        //editor.putString("tv1_text", binding.tv1.text.toString())
        //editor.apply()
    }
*/

}