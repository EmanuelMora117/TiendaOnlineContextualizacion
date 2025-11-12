package com.example.tiendaonline.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Button
import android.widget.ImageView
import android.net.Uri
import android.view.Gravity
import com.example.tiendaonline.R
import com.example.tiendaonline.database.dbCarritoOperations

class CarritoActivity : ComponentActivity() {

    private lateinit var tabla: TableLayout
    private lateinit var tvTotal: TextView
    private lateinit var btnVaciar: Button
    private lateinit var btnVolver: Button
    private lateinit var dbOpsCarrito: dbCarritoOperations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        tabla = findViewById(R.id.tableProductos)
        tvTotal = findViewById(R.id.tvTotal)
        btnVaciar = findViewById(R.id.btnVaciar)
        btnVolver = findViewById(R.id.btnVolver)
        dbOpsCarrito = dbCarritoOperations(this)
        mostrarProductos()

        btnVaciar.setOnClickListener {
            dbOpsCarrito.deleteAllProductsCarrito()
            mostrarProductos()
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this, ListaProductosActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun mostrarProductos() {

        tabla.removeAllViews()
        var total = 0.0

        for (producto in dbOpsCarrito.getProductsCarrito()) {
            val fila = TableRow(this).apply {
                setPadding(8, 8, 8, 8)
            }

            val ivImagen = ImageView(this).apply {
                layoutParams = TableRow.LayoutParams(200, 200)
                scaleType = ImageView.ScaleType.CENTER_CROP
                if (producto.imagen != null) {
                    setImageURI(Uri.parse(producto.imagen))
                } else {
                    setImageResource(R.drawable.ic_launcher_foreground)
                }
            }

            val tvNombre = TextView(this).apply {
                text = producto.nombre
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }

            val tvDescripcion = TextView(this).apply {
                text = producto.descripcion
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }

            val tvPrecio = TextView(this).apply {
                text = "$${producto.precio.toInt()}"
                textSize = 16f
                setPadding(16, 8, 16, 8)
                gravity = Gravity.END
            }

            fila.addView(ivImagen)
            fila.addView(tvNombre)
            fila.addView(tvDescripcion)
            fila.addView(tvPrecio)

            tabla.addView(fila)
            total += producto.precio
        }

        tvTotal.text = "Total: $${total.toInt()}"
    }
}



@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    TiendaOnlineTheme {
        Greeting4("Android")
    }
}