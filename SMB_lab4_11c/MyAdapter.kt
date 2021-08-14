package com.example.myapp11

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp11.databinding.ListElementBinding

class MyAdapter(val studentViewModel: StudentViewModel) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var studentList = emptyList<Student>()

    class MyViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvId.text = studentList[position].id.toString()
        holder.binding.tvName.text = studentList[position].name
        holder.binding.tvSur.text = studentList[position].surname
        holder.binding.cbAbsolwent.isChecked = studentList[position].absolwent
        holder.binding.root.setOnClickListener {
            studentViewModel.delete(studentList[position])
            notifyDataSetChanged()
        }
        holder.binding.cbAbsolwent.setOnClickListener {
            studentList[position].absolwent = holder.binding.cbAbsolwent.isChecked
            studentViewModel.update(studentList[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = studentList.size

    fun setStudents(students: List<Student>){
        this.studentList = students
        notifyDataSetChanged()
    }

}