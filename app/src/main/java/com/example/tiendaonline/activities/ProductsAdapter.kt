package com.example.tiendaonline.adapters


/*class ProductsAdapter(
    private val productos: MutableList<Producto>,
    private val onEdit: (Producto) -> Unit,
    private val onDelete: (Producto) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_admin, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = "Precio: $${producto.precio}"

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

        holder.btnEditar.setOnClickListener { onEdit(producto) }
        holder.btnEliminar.setOnClickListener { onDelete(producto) }
    }

    override fun getItemCount(): Int = productos.size
}*/



/*class ProductsAdapter(
    private val productos: MutableList<Producto>,
    private val onEdit: (Producto) -> Unit,
    private val onDelete: (Producto) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_admin, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = "Precio: $${producto.precio}"

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

        holder.btnEditar.setOnClickListener { onEdit(producto) }
        holder.btnEliminar.setOnClickListener { onDelete(producto) }
    }

    override fun getItemCount(): Int = productos.size
}*/



/*class ProductsAdapter(
    private val productos: MutableList<Producto>,
    private val onEdit: (Producto) -> Unit,
    private val onDelete: (Producto) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_admin, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = "Precio: $${producto.precio}"

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

        holder.btnEditar.setOnClickListener { onEdit(producto) }
        holder.btnEliminar.setOnClickListener { onDelete(producto) }
    }

    override fun getItemCount(): Int = productos.size
}*/
