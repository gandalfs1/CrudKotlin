package com.example.crud

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.crud.models.Student


class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1;
        private const val DATABASE_NAME = "student.db";
        private const val TABLE_NAME = "table_student";
        private const val ID = "id";
        private const val NAME = "name";
        private const val EMAIL = "email";
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStudent = ("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," +
                EMAIL + " TEXT " + ")")

        db?.execSQL(createTableStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    /**
     * inserta estudiantes en la base de datos
     *
     * recibe por parametro un modelo de estudiante
     * retorna un long
     */
    fun insertStudent(student: Student): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, student.id)
        contentValues.put(NAME, student.name)
        contentValues.put(EMAIL, student.email)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return  success
    }

    /**
     * devuelve todos los estudiantes
     */
    @SuppressLint("Range")
    fun getAllStudent(): ArrayList<Student>{

        val studentList : ArrayList<Student> = ArrayList()
        val selectQuery = " SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var name:String
        var email:String

        if (cursor.moveToFirst()){
            do{
                id= cursor.getInt(cursor.getColumnIndex("id"))
                name= cursor.getString(cursor.getColumnIndex("name"))
                email= cursor.getString(cursor.getColumnIndex("email"))
                val std = Student(id = id,name = name, email= email)
                studentList.add(std)
            }while (cursor.moveToNext())
        }
        return studentList
    }

    /**
     * metodo que actualizar un estudiante de la base de datos
     */
    fun updateStudent(student: Student): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, student.id)
        contentValues.put(NAME, student.name)
        contentValues.put(EMAIL, student.email)

        val success = db.update(TABLE_NAME, contentValues, "id="+student.id, null)
        db.close()
        return success
    }
}