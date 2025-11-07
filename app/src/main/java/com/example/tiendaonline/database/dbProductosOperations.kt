package com.example.tiendaonline.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.tiendaonline.models.Producto

class dbProductsOperations(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insertProducts(producto: Producto): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", producto.nombre)
            put("precio", producto.precio)
            put("descripcion", producto.descripcion)
            put("imagenRuta", producto.imagen)
        }
        val id = db.insert("productos", null, values)
        db.close()
        return id
    }

    fun getProducts(): MutableList<Producto> {
        val lista = mutableListOf<Producto>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM productos", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val imagenRuta = cursor.getString(cursor.getColumnIndexOrThrow("imagenRuta"))

                lista.add(Producto(id, nombre, precio, descripcion, imagenRuta))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    fun getProductById(id: Int): Producto? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM productos WHERE id = ?", arrayOf(id.toString()))
        var producto: Producto? = null
        if (cursor.moveToFirst()) {
            producto = Producto(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio")),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagenRuta"))
            )
        }
        cursor.close()
        db.close()
        return producto
    }


    fun deleteProducts(id: Int): Int {
        val db = dbHelper.writableDatabase
        val filas = db.delete("productos", "id=?", arrayOf(id.toString()))
        db.close()
        return filas
    }

    fun updateProducts(producto: Producto): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", producto.nombre)
            put("precio", producto.precio)
            put("descripcion", producto.descripcion)
            put("imagenRuta", producto.imagen)
        }
        val filas = db.update("productos", values, "id=?", arrayOf(producto.id.toString()))
        db.close()
        return filas
    }
}