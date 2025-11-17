package com.example.tiendaonline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.models.Producto
import com.example.tiendaonline.R

class ProductsAdapter(
    private val products: List<Producto>,
    private val origin: String,
    private val onEdit: ((Producto) -> Unit)? = null,
    private val onDelete: ((Producto) -> Unit)? = null,
    private val onAddCar: ((Producto) -> Unit)? = null
) : RecyclerView.Adapter<ProductsAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionProducto)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioProducto)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
        val btnAgregar: Button = view.findViewById(R.id.btnAgregarCarrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = products[position]

        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion ?: ""
        holder.tvPrecio.text = "$${producto.precio}"

        val imagenUri = producto.imagen
        if (!imagenUri.isNullOrEmpty()) {
            try {
                holder.imgProducto.setImageURI(Uri.parse(imagenUri))
            } catch (e: Exception) {
                holder.imgProducto.setImageResource(android.R.color.darker_gray)
            }
        } else {
            holder.imgProducto.setImageResource(android.R.color.darker_gray)
        }

        holder.btnEditar.visibility = View.GONE
        holder.btnEliminar.visibility = View.GONE
        holder.btnAgregar.visibility = View.GONE

        when (origin) {
            "admin" -> {
                holder.btnEditar.visibility = View.VISIBLE
                holder.btnEliminar.visibility = View.VISIBLE

                holder.btnEditar.setOnClickListener { onEdit?.invoke(producto) }
                holder.btnEliminar.setOnClickListener { onDelete?.invoke(producto) }
            }
            "lista" -> {

                holder.btnAgregar.visibility = View.VISIBLE
                holder.btnAgregar.setOnClickListener { onAddCar?.invoke(producto) }
            }
        }
    }

    override fun getItemCount(): Int = products.size
}
