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
            showMessage("Debe ingresar todos los datos")
            return;
        }
        val std = Student(name = nameData, email = emailData)
        val status = sqLiteHelper.insertStudent(std)

        if (status > -1) {
            showMessage("Datos Agregados Correctamente")
            clearEditext()
        } else {
            showMessage("Error Al Agregar Datos")
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

    private fun showMessage(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}