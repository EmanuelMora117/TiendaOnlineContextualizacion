package com.example.tiendaonline

data class Producto(val nombre: String, val precio: Double, val imagen: Int)

object Carrito {
    val productos = mutableListOf<Producto>()
}