package com.example.tiendaonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class ListaProductosActivity : ComponentActivity() {

    private val listaProductos = listOf(
        Producto("Camiseta deportiva", 75000.0, R.drawable.camisadeportiva),
        Producto("Tenis running", 220000.0, R.drawable.tenniszapatos),
        Producto("Gorra Nike", 45000.0, R.drawable.gorra),
        Producto("Pantaloneta", 60000.0, R.drawable.pantaloneta),
        Producto("Chaqueta deportiva", 180000.0, R.drawable.chaquetadeportiva)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        val contenedor = findViewById<LinearLayout>(R.id.layoutProductos)
        val btnVerCarrito = findViewById<Button>(R.id.btnVerCarrito)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        contenedor.removeAllViews()

        for (producto in listaProductos) {
            val itemLayout = LinearLayout(this)
            itemLayout.orientation = LinearLayout.VERTICAL
            itemLayout.setPadding(0, 10, 0, 10)

            val imagen = ImageView(this)
            imagen.setImageResource(producto.imagen)
            imagen.adjustViewBounds = true
            imagen.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                400
            )

            val nombre = TextView(this)
            nombre.text = producto.nombre
            nombre.textSize = 18f

            val precio = TextView(this)
            precio.text = "Precio: $${producto.precio}"
            precio.textSize = 16f

            val boton = Button(this)
            boton.text = "Agregar al carrito"
            boton.setOnClickListener {
                Carrito.productos.add(producto)
                Toast.makeText(this, "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
            }

            val separador = View(this)
            separador.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
            )
            separador.setBackgroundColor(resources.getColor(android.R.color.darker_gray))

            itemLayout.addView(imagen)
            itemLayout.addView(nombre)
            itemLayout.addView(precio)
            itemLayout.addView(boton)
            itemLayout.addView(separador)

            contenedor.addView(itemLayout)
        }

        btnVerCarrito.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }


}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    TiendaOnlineTheme {
        Greeting3("Android")
    }
}