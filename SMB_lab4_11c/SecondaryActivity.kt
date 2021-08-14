package com.example.myapp11

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp11.databinding.ActivitySecondaryBinding
import kotlinx.android.synthetic.main.activity_main.*

class SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        binding.textView.text = intent.getStringExtra("tv1text")

        binding.rv1.layoutManager = LinearLayoutManager(this) //LayoutManager
        binding.rv1.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ) //(optional) DividerItemDecoration

        val studentViewModel = StudentViewModel(application)
        val adapter = MyAdapter(studentViewModel)
        binding.rv1.adapter = adapter
        studentViewModel.allStudents.observe(this, Observer { students ->
            students.let{
                adapter.setStudents(it)
            }
        })
        binding.button.setOnClickListener {
            studentViewModel.insert(Student(
                binding.etName.text.toString(),
                binding.etSurname.text.toString(),
                binding.checkBox.isChecked)
            )
            val broadcastIntent = Intent(getString(R.string.dodanieStudenta))
            broadcastIntent.putExtra("nazwa", binding.etName.text.toString())
            broadcastIntent.component = ComponentName(this, MyReceiver::class.java)
            sendBroadcast(broadcastIntent)
        }
        binding.button.setOnLongClickListener {
            studentViewModel.deleteAll()
            true
        }
    }
}