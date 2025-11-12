package com.example.tiendaonline.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.tiendaonline.models.Producto

class dbCarritoOperations(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insertCarritoProducts(producto: Producto): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", producto.nombre)
            put("precio", producto.precio)
            put("descripcion", producto.descripcion)
            put("imagenRuta", producto.imagen)
        }
        val id = db.insert("carrito", null, values)
        db.close()
        return id
    }

    fun getProductsCarrito(): MutableList<Producto> {
        val lista = mutableListOf<Producto>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM carrito", null)

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


    fun deleteAllProductsCarrito(){
        val db = dbHelper.writableDatabase
        try {
            db.execSQL("DELETE FROM carrito")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }
}