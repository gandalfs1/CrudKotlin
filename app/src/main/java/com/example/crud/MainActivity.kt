package com.example.crud

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.adapters.StudentAdapter
import com.example.crud.models.Student

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerview : RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener{ getStudent() }
        adapter?.setOnclickItem {  showMessage("click ${it.name}")
            edName.setText(it.name)
            edEmail.setText(it.email)
            std = it
        }
        btnUpdate.setOnClickListener { updateStudent() }

        adapter?.setOnClickDeleteItem { showMessage("se eliminara ${it.name}") }
    }

    private fun updateStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty() ){
            showMessage("debe seleccionar un estudiante de lista")
            return
        }

        if(std == null || std?.id == 0){
            showMessage("debe registar primero antes de actualizarlo")
            return
        }

        val std = Student(id = std!!.id, name = name, email = email)
        val status = sqLiteHelper.updateStudent(std)
        if (status > -1) {
            showMessage("Datos Actualizado Correctamente")
            clearEditext()
            getStudent()
        } else {
            showMessage("Error Al Actualizar Datos")
        }
    }

    private fun getStudent() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("listStudent", "${stdList.size}")
        adapter?.additems(stdList)
    }

    private fun initRecyclerView(){
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerview.adapter = adapter
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
            getStudent()
        } else {
            showMessage("Error Al Agregar Datos")
        }

    }

    private fun clearEditext() {
        std?.id = 0
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }

    private fun initView() {
        edName = findViewById(R.id.nameUser)
        edEmail = findViewById(R.id.emailUser)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        recyclerview = findViewById(R.id.recyclerView)
        btnUpdate = findViewById(R.id.btnUpdate)
    }

    private fun showMessage(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}