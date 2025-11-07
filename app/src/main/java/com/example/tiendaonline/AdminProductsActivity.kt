package com.example.tiendaonline

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.database.dbProductsOperations
import com.example.tiendaonline.models.Producto

class AdminProductosActivity : ComponentActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductsAdapter
    private lateinit var dbOps: dbProductsOperations
    private lateinit var listaProductos: MutableList<Producto>
    private lateinit var btnAgregar: Button
    private lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_products)

        recycler = findViewById(R.id.recyclerProductos)
        dbOps = dbProductsOperations(this)
        listaProductos = dbOps.getProducts()
        btnAgregar = findViewById(R.id.btnAgregarProducto)
        btnRegresar = findViewById(R.id.btnRegresar)

        adapter = ProductsAdapter(
            listaProductos,
            onEdit = { producto ->
                val intent = Intent(this, FormProductoActivity::class.java)
                intent.putExtra("producto_id", producto.id)
                startActivity(intent)
            },
            onDelete = { producto ->
                dbOps.deleteProducts(producto.id)
                listaProductos.remove(producto)
                adapter.notifyDataSetChanged()
            }
        )

        btnAgregar.setOnClickListener {
            val intent = Intent(this, FormProductoActivity::class.java)
            startActivity(intent)
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        listaProductos.clear()
        listaProductos.addAll(dbOps.getProducts())
        adapter.notifyDataSetChanged()
    }
}

@Composable
fun Greeting6(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview6() {
    TiendaOnlineTheme {
        Greeting6("Android")
    }
}