package com.example.crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crud.models.Student

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }
    }

    private fun addStudent() {
        val nameData = edName.text.toString()
        val emailData = edEmail.text.toString()

        if (nameData.isEmpty() || emailData.isEmpty()) {
            Toast.makeText(this, "ingrese datos", Toast.LENGTH_SHORT).show()
        } else {
            val std = Student(name = nameData, email = emailData)
            val status = sqLiteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this, "Datos Agregados Correctamente", Toast.LENGTH_SHORT).show()
                clearEditext()
            }else{
                Toast.makeText(this, "Error Al Agregar Datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditext() {
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }

    private fun initView() {
        edName = findViewById(R.id.nameUser)
        edEmail = findViewById(R.id.emailUser)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
    }
}