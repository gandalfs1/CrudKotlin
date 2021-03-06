package com.example.crud.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.R
import com.example.crud.models.Student

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var stdList: ArrayList<Student> = ArrayList()
    private var onClickItem: ((Student) -> Unit)? = null
    private var onClickDeleteItem: ((Student) -> Unit)? = null

    fun additems(items: ArrayList<Student>) {
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickDeleteItem(callback: (Student) ->Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_item_student, parent, false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(std)}
    }

    fun setOnclickItem(callback: (Student) -> Unit) {
        this.onClickItem = callback
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: Student) {
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email
        }
    }
}