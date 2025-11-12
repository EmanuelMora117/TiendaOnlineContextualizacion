package com.example.tiendaonline.activities

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaonline.database.dbProductsOperations
import com.example.tiendaonline.models.Producto
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme
import java.io.File
import java.io.FileOutputStream
import android.widget.Button
import com.example.tiendaonline.R


class FormProductoActivity : ComponentActivity() {

    private lateinit var imgPreview: ImageView
    private lateinit var inputNombre: EditText
    private lateinit var inputPrecio: EditText
    private lateinit var inputDescripcion: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnCancelar: Button
    private var imagenSeleccionada: Uri? = null
    private lateinit var dbOperations: dbProductsOperations
    private var productoExistente: Producto? = null

    private val seleccionarImagen =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val file = File(filesDir, "imagen_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                imagenSeleccionada = Uri.fromFile(file)
                imgPreview.setImageURI(imagenSeleccionada)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_products)

        dbOperations = dbProductsOperations(this)
        imgPreview = findViewById(R.id.imgPreview)
        inputNombre = findViewById(R.id.inputNombre)
        inputPrecio = findViewById(R.id.inputPrecio)
        inputDescripcion = findViewById(R.id.inputDescripcion)
        btnGuardar = findViewById(R.id.btnGuardarProducto)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnCancelar = findViewById(R.id.btnCancelar)

        val productoId = intent.getIntExtra("producto_id", -1)
        if (productoId != -1) {
            productoExistente = dbOperations.getProductById(productoId)
            productoExistente?.let { cargarDatosProducto(it) }
        }

        btnSeleccionarImagen.setOnClickListener {
            seleccionarImagen.launch("image/*")
        }
        btnGuardar.setOnClickListener {
            guardarProducto()
        }
        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun cargarDatosProducto(producto: Producto) {
        inputNombre.setText(producto.nombre)
        inputPrecio.setText(producto.precio.toString())
        inputDescripcion.setText(producto.descripcion)

        producto.imagen?.let { ruta ->
            val archivo = File(Uri.parse(ruta).path ?: "")
            if (archivo.exists()) {
                imgPreview.setImageURI(Uri.fromFile(archivo))
                imagenSeleccionada = Uri.fromFile(archivo)
            }
        }
        btnGuardar.text = "Actualizar producto"
    }


    private fun guardarProducto() {
        val nombre = inputNombre.text.toString().trim()
        val precio = inputPrecio.text.toString().toDoubleOrNull()
        val descripcion = inputDescripcion.text.toString().trim()
        val imagenUri = imagenSeleccionada?.toString()

        if (nombre.isEmpty() || precio == null) {
            Toast.makeText(this, "Por favor completa nombre y precio", Toast.LENGTH_SHORT).show()
            return
        }

        if (productoExistente != null) {
            val productoActualizado = productoExistente!!.copy(
                nombre = nombre,
                precio = precio,
                descripcion = descripcion,
                imagen = imagenUri
            )
            dbOperations.updateProducts(productoActualizado)
            Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            dbOperations.insertProducts(
                Producto(
                    nombre = nombre,
                    precio = precio,
                    descripcion = descripcion,
                    imagen = imagenUri
                )
            )
            Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}

@Composable
fun Greeting7(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview7() {
    TiendaOnlineTheme {
        Greeting7("Android")
    }
}
