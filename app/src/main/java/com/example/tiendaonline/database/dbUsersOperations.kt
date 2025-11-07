package com.example.tiendaonline.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.tiendaonline.models.User

class dbUsersOperations(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insertUser(user: User): Boolean {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE correo=?", arrayOf(user.email))
        val exists = cursor.count > 0
        cursor.close()

        return if (exists) {
            false
        } else {
            val values = ContentValues().apply {
                put("nombre", user.nameUser)
                put("correo", user.email)
                put("password", user.password)
            }
            db.insert("users", null, values)
            true
        }
    }

    fun validateUser(nombre: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE nombre=? AND password=?",
            arrayOf(nombre, password)
        )

        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()
        return existe
    }
}