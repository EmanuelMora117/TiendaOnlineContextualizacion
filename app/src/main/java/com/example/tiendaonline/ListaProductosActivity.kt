package com.example.tiendaonline

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.tiendaonline.database.dbProductsOperations
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme
import com.google.android.gms.location.LocationServices
import android.location.Geocoder
import android.view.View
import androidx.core.content.ContextCompat
import com.example.tiendaonline.database.dbCarritoOperations
import com.example.tiendaonline.database.dbUsersOperations
import com.example.tiendaonline.models.Producto
import java.util.*
import com.google.android.gms.location.FusedLocationProviderClient



class ListaProductosActivity : ComponentActivity() {

    private lateinit var dbOps: dbProductsOperations
    private lateinit var dbOpsCarrito: dbCarritoOperations
    private lateinit var contenedor: LinearLayout
    private lateinit var btnVerCarrito: Button
    private lateinit var btnAdminProducts: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var tvUbicacion: TextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val permisoUbicacionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            obtenerUbicacionAproximada()
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        contenedor = findViewById(R.id.layoutProductos)
        btnVerCarrito = findViewById(R.id.btnVerCarrito)
        btnAdminProducts = findViewById(R.id.btnAdminProducts)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        tvUbicacion = findViewById(R.id.tvUbicacion)
        dbOps = dbProductsOperations(this)
        dbOpsCarrito = dbCarritoOperations(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            obtenerUbicacionAproximada()
        } else {
            permisoUbicacionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

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
        dbOps?.let {
            cargarProductos()
        }
    }

    private fun cargarProductos() {
        contenedor.removeAllViews()
        val listaProductos = dbOps.getProducts()

        for (producto in listaProductos) {
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 20, 0, 20) }
                setPadding(16, 16, 16, 16)
                background = GradientDrawable().apply {
                    cornerRadius = 20f
                    setStroke(3, Color.LTGRAY)
                    setColor(Color.WHITE)
                }
            }

            val img = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    500
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
                if (producto.imagen != null) setImageURI(Uri.parse(producto.imagen))
                else setBackgroundColor(Color.LTGRAY)
            }

            val tvNombre = TextView(this).apply {
                text = producto.nombre
                textSize = 20f
                setTextColor(Color.BLACK)
                setPadding(0, 16, 0, 8)
            }

            val tvPrecio = TextView(this).apply {
                text = "Precio: $${producto.precio}"
                textSize = 16f
                setTextColor(Color.DKGRAY)
            }

            val btnAgregar = Button(this).apply {
                text = "Agregar al carrito"
                setBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
                setTextColor(Color.WHITE)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = 16 }

                setOnClickListener {
                    dbOpsCarrito.insertCarritoProducts(
                        Producto(
                            nombre = producto.nombre,
                            precio = producto.precio,
                            descripcion = producto.descripcion,
                            imagen = producto.imagen
                        )
                    )
                    Toast.makeText(this@ListaProductosActivity, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()
                    this.visibility = View.GONE
                }

            }

            itemLayout.apply {
                addView(img)
                addView(tvNombre)
                addView(tvPrecio)
                addView(btnAgregar)
            }

            contenedor.addView(itemLayout)
        }
    }

    private fun obtenerUbicacionAproximada() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permiso de ubicación no concedido", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                Thread {
                    try {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val ciudad = addresses?.get(0)?.locality ?: "Desconocida"
                        val pais = addresses?.get(0)?.countryName ?: "Desconocido"

                        runOnUiThread {
                            tvUbicacion.text = "Ubicación: $ciudad, $pais"
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            tvUbicacion.text = "Ubicación: No disponible"
                        }
                    }
                }.start()
            } else {
                tvUbicacion.text = "Ubicación: No disponible"
            }
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