package com.example.tiendaonline.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.R
import com.example.tiendaonline.adapters.ProductsAdapter
import com.example.tiendaonline.database.dbCarritoOperations
import com.example.tiendaonline.database.dbProductsOperations
import com.example.tiendaonline.models.Producto
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme

class ListaProductosActivity : ComponentActivity() {

    private lateinit var dbOps: dbProductsOperations
    private lateinit var dbOpsCarrito: dbCarritoOperations
    private lateinit var recyclerProductos: RecyclerView
    private lateinit var btnVerCarrito: Button
    private lateinit var btnAdminProducts: Button
    private lateinit var btnCerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        recyclerProductos = findViewById(R.id.recyclerProductos)
        btnVerCarrito = findViewById(R.id.btnVerCarrito)
        btnAdminProducts = findViewById(R.id.btnAdminProducts)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        dbOps = dbProductsOperations(this)
        dbOpsCarrito = dbCarritoOperations(this)

        recyclerProductos.layoutManager = LinearLayoutManager(this)

        btnVerCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }

        btnAdminProducts.setOnClickListener {
            startActivity(Intent(this, AdminProductosActivity::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        cargarProductos()
    }

    override fun onResume() {
        super.onResume()
        cargarProductos()
    }

    private fun cargarProductos() {
        val listaProductos = dbOps.getProducts()

        val adapter = ProductsAdapter(
            products = listaProductos,
            origin = "lista",
            onAddCar = { producto ->
                dbOpsCarrito.insertCarritoProducts(
                    Producto(
                        nombre = producto.nombre,
                        precio = producto.precio,
                        descripcion = producto.descripcion,
                        imagen = producto.imagen
                    )
                )
                Toast.makeText(this, "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerProductos.adapter = adapter
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello aaaa $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    TiendaOnlineTheme {
        Greeting2("Android")
    }
}
